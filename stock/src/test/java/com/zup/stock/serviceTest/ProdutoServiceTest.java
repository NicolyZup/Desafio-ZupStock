package com.zup.stock.serviceTest;

import com.zup.stock.model.ProdutoModel;
import com.zup.stock.model.dto.ProdutoAllDTO;
import com.zup.stock.model.dto.ProdutoPutDTO;
import com.zup.stock.repository.ProdutoRepository;
import com.zup.stock.service.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Test
    public void testarListarProdutos(){
        List<ProdutoModel> produtoModels = new ArrayList<>();
        produtoModels.add(new ProdutoModel(1L,"Arroz", "Arroz branco soltinho", BigDecimal.valueOf(17.90),20));
        produtoModels.add(new ProdutoModel(2L,"Feijão", "Feijão preto solito", BigDecimal.valueOf(13.00),15));

        when(produtoRepository.findAll()).thenReturn(produtoModels);

        List<ProdutoAllDTO> produtosDTOs = produtoService.listar();

        Assertions.assertEquals(produtoModels.size(),produtosDTOs.size());

        for(int i = 0; i < produtoModels.size(); i++){
            ProdutoModel produtoModel = produtoModels.get(i);
            ProdutoAllDTO produtoAllDTO = produtosDTOs.get(i);

            Assertions.assertEquals(produtoModel.getNome(), produtoAllDTO.getNome());
            Assertions.assertEquals(produtoModel.getDescricao(), produtoAllDTO.getDescricao());
            Assertions.assertEquals(produtoModel.getPreco(), produtoAllDTO.getPreco());
            Assertions.assertEquals(produtoModel.getQuantidade(), produtoAllDTO.getQuantidade());
        }
    }

    @Test
    public void testarListarPorIdExistente(){
        ProdutoModel produtoModel = new ProdutoModel(3L,"Óleo", "Óleo de soja solito", BigDecimal.valueOf(10.90),12);

        when(produtoRepository.findById(produtoModel.getId())).thenReturn(Optional.of(produtoModel));

        Optional<ProdutoAllDTO> produtoAllDTO = produtoService.listarPorId(produtoModel.getId());

        Assertions.assertTrue(produtoAllDTO.isPresent());

        Assertions.assertEquals(produtoModel.getNome(),produtoAllDTO.get().getNome());
        Assertions.assertEquals(produtoModel.getDescricao(),produtoAllDTO.get().getDescricao());
        Assertions.assertEquals(produtoModel.getPreco(),produtoAllDTO.get().getPreco());
        Assertions.assertEquals(produtoModel.getQuantidade(),produtoAllDTO.get().getQuantidade());
    }

    @Test
    public void testarListarPorIdNaoExistente(){
        when(produtoRepository.findById(4L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            produtoService.listarPorId(4L);
        });
    }

    @Test
    public void testarCriarProduto(){
        ProdutoAllDTO produtoAllDTO = new ProdutoAllDTO("Papel Higiênico", "Papel higiênico com 16 rolos", BigDecimal.valueOf(10.00), 25);

        ProdutoAllDTO produtoCriado = produtoService.criar(produtoAllDTO);

        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(produtoAllDTO.getNome());
        produtoModel.setDescricao(produtoAllDTO.getDescricao());
        produtoModel.setPreco(produtoAllDTO.getPreco());
        produtoModel.setQuantidade(produtoAllDTO.getQuantidade());

        verify(produtoRepository,times(1)).save(any(produtoModel.getClass()));
        Assertions.assertEquals(produtoAllDTO,produtoCriado);
    }

    @Test
    public void testarAlterarProduto(){
        ProdutoPutDTO produtoPutDTO = new ProdutoPutDTO("Amaciante Ypê", "Amaciante ypê - flores", BigDecimal.valueOf(18.00),12);

        ProdutoModel produtoModel = new ProdutoModel(1L,"Amaciante Omo", "Amaciante ypê - flores", BigDecimal.valueOf(18.00),12);
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produtoModel));

        ProdutoPutDTO produtoAlterado = produtoService.alterar(1L, produtoPutDTO);

        verify(produtoRepository,times(1)).save(produtoModel);

        Assertions.assertEquals(produtoPutDTO.getNome(),produtoAlterado.getNome());
        Assertions.assertEquals(produtoPutDTO.getDescricao(),produtoAlterado.getDescricao());
        Assertions.assertEquals(produtoPutDTO.getPreco(),produtoAlterado.getPreco());
        Assertions.assertEquals(produtoPutDTO.getQuantidade(),produtoAlterado.getQuantidade());
    }

    @Test
    public void testarExcluirProduto(){
        ProdutoModel produtoModel = new ProdutoModel(8L,"Detergente", "Detergente ypê - neutro", BigDecimal.valueOf(2.00),30);
        when(produtoRepository.findById(produtoModel.getId())).thenReturn(Optional.of(produtoModel));

        produtoService.excluir(produtoModel.getId());

        verify(produtoRepository, times(1)).deleteById(produtoModel.getId());
    }
}
