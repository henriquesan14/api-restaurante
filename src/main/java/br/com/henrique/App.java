package br.com.henrique;


import br.com.henrique.domain.*;
import br.com.henrique.domain.enums.Perfil;
import br.com.henrique.domain.enums.StatusMesa;
import br.com.henrique.domain.enums.TipoCategoria;
import br.com.henrique.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private MesaRepository mesaRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria c1 = new Categoria(null,"Refrigerantes", TipoCategoria.BEBIDA);
        Categoria c2 = new Categoria(null,"Sanduiches", TipoCategoria.REFEICAO);
        Produto p1 = new Produto(null,"Coca-cola", BigDecimal.valueOf(8),c1);
        Produto p2 = new Produto(null,"Fanta", BigDecimal.valueOf(7),c1);
        Produto p3 = new Produto(null,"X-burguer", BigDecimal.valueOf(5),c2);
        Produto p4 = new Produto(null,"X-calabresa", BigDecimal.valueOf(6),c2);
        categoriaRepository.saveAll(Arrays.asList(c1,c2));
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4));

        Usuario u = new Usuario(null,"Henrique","Santos","11111111","henrique@gmail.com","55555555","admin","admin");
        Endereco e = new Endereco(null,"ekapoke","eapkeoak","eakpoeka","epakea","eakeopa",u);
        u.getEnderecos().add(e);
        u.addPerfil(Perfil.ADMIN);

        Usuario u2 = new Usuario(null,"Xablau","Santos","222222","xablau@gmail.com","4434331111","admin","admin");
        Endereco e2 = new Endereco(null,"ekapoke","eapkeoak","eakpoeka","epakea","eakeopa",u2);
        u2.getEnderecos().add(e2);
        u2.addPerfil(Perfil.CLIENTE);

        usuarioRepository.saveAll(Arrays.asList(u,u2));

        Mesa m = new Mesa(null,"Mesa", StatusMesa.DISPONIVEL);
        mesaRepository.save(m);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1= new Pedido(null,sdf.parse("30/09/2017 10:32"),m,u2,u);
        ItemPedido i = new ItemPedido(ped1,p1,1,BigDecimal.valueOf(8));
        ped1.getItens().add(i);
        pedidoRepository.save(ped1);
    }
}
