package com.zup.stock.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zup.stock.controller.ProdutoController;
import com.zup.stock.model.dto.ProdutoAllDTO;
import com.zup.stock.model.dto.ProdutoPutDTO;
import com.zup.stock.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig
@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoService produtoService;

    @Test
    public void testarListarSemProdutos() throws Exception{

        Mockito.when(produtoService.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/ZupStock/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().string("Nenhum produto cadastrado ainda."))
                .andDo(print());
    }

    @Test
    public void testarListarComProdutos() throws Exception{
        ProdutoAllDTO produto1 = new ProdutoAllDTO("Arroz Paletó","Arroz branco soltinho",
                BigDecimal.valueOf(13.00),20);

        ProdutoAllDTO produto2 = new ProdutoAllDTO("Feijão Solito","Feijão branco solito",
                BigDecimal.valueOf(10.90),12);

        Mockito.when(produtoService.listar()).thenReturn(List.<ProdutoAllDTO>of(produto1,produto2));

        mockMvc.perform(get("/api/ZupStock/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Arroz Paletó"))
                .andExpect(jsonPath("$[0].descricao").value("Arroz branco soltinho"))
                .andExpect(jsonPath("$[0].preco").value(13.00))
                .andExpect(jsonPath("$[0].quantidade").value(20))
                .andExpect(jsonPath("$[1].nome").value("Feijão Solito"))
                .andExpect(jsonPath("$[1].descricao").value("Feijão branco solito"))
                .andExpect(jsonPath("$[1].preco").value(10.90))
                .andExpect(jsonPath("$[1].quantidade").value(12))
                .andExpect(jsonPath("$.length()").value(2))
                .andDo(print());
    }

    @Test
    public void testarListarProdutoPorId() throws Exception{
        ProdutoAllDTO produto = new ProdutoAllDTO("Frango","Frango sadia 1 kg",BigDecimal.valueOf(17.80),10);

        Mockito.when(produtoService.listarPorId(1L)).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/api/ZupStock/produtos/{1}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Frango"))
                .andExpect(jsonPath("$.descricao").value("Frango sadia 1 kg"))
                .andExpect(jsonPath("$.preco").value(17.80))
                .andExpect(jsonPath("$.quantidade").value(10))
                .andDo(print());
    }

    @Test
    public void testarCadastroDeProduto() throws Exception{
        ProdutoAllDTO produto = new ProdutoAllDTO("Papel Higiênico","Papel 30 metros",BigDecimal.valueOf(7.90),18);

        Mockito.when(produtoService.criar(Mockito.any(produto.getClass()))).thenReturn(produto);

        mockMvc.perform(post("/api/ZupStock/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Papel Higiênico"))
                .andExpect(jsonPath("$.descricao").value("Papel 30 metros"))
                .andExpect(jsonPath("$.preco").value(7.90))
                .andExpect(jsonPath("$.quantidade").value(18))
                .andDo(print());
    }

    @Test
    public void testarEditarProduto() throws  Exception{
        ProdutoPutDTO produto = new ProdutoPutDTO("Arroz Integral","Arroz integral soltinho", BigDecimal.valueOf(19.90), 23);

        Mockito.when(produtoService.alterar(1L, produto)).thenReturn(produto);

        mockMvc.perform(put("/api/ZupStock/produtos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(produto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Arroz Integral"))
                .andExpect(jsonPath("$.descricao").value("Arroz integral soltinho"))
                .andExpect(jsonPath("$.preco").value(19.90))
                .andExpect(jsonPath("$.quantidade").value(23))
                .andDo(print());
    }

    @Test
    public void testarDeletarProduto() throws Exception{
        mockMvc.perform(delete("/api/ZupStock/produtos/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().string("Produto excluído com sucesso!"))
                .andDo(print());
    }
}
