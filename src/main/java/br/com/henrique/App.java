package br.com.henrique;


import br.com.henrique.domain.*;
import br.com.henrique.domain.enums.Perfil;
import br.com.henrique.domain.enums.StatusMesa;
import br.com.henrique.domain.enums.StatusPedido;
import br.com.henrique.domain.enums.TipoCategoria;
import br.com.henrique.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder pe;

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
        Produto p5 = new Produto(null,"X-Salada", BigDecimal.valueOf(4),c2);
        Produto p6 = new Produto(null,"Lanche 1", BigDecimal.valueOf(6),c2);
        Produto p7 = new Produto(null,"Lanche 2", BigDecimal.valueOf(6),c2);
        Produto p8 = new Produto(null,"Lanche 3", BigDecimal.valueOf(6),c2);
        Produto p9 = new Produto(null,"Lanche 4", BigDecimal.valueOf(6),c2);
        Produto p10= new Produto(null,"Guaraná", BigDecimal.valueOf(6),c1);
        Produto p11= new Produto(null,"Suco", BigDecimal.valueOf(3),c1);
        categoriaRepository.saveAll(Arrays.asList(c1,c2));
        produtoRepository.saveAll(Arrays.asList(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11));

        Usuario u = new Usuario(null,"Henrique","Santos","017.828.044-55","henrique@gmail.com","(81)99999-9999",pe.encode("123456"));
        Endereco e = new Endereco(null,"Rua das flores","123","Centro","","58328-000",u);
        u.getEnderecos().add(e);
        u.addPerfil(Perfil.ADMIN);

        Usuario u3 = new Usuario(null,"Garçom","Santos","017.828.044-55","gg@gmail.com","(81)99999-9999",pe.encode("123456"));
        u3.getEnderecos().add(e);
        u3.addPerfil(Perfil.GARCOM);

        Usuario u2 = new Usuario(null,"Xablau","Santos","017.828.044-55","xablau@gmail.com","(81)99999-9999",pe.encode("123456"));
        Endereco e2 = new Endereco(null,"Rua do sol","547","Mangueira","Apto 505","58214-000",u2);
        u2.getEnderecos().add(e2);
        u2.addPerfil(Perfil.CLIENTE);

        usuarioRepository.saveAll(Arrays.asList(u,u2,u3));

        Mesa m = new Mesa(null,"Mesa001");
        Mesa m2 = new Mesa(null,"Mesa002");
        mesaRepository.saveAll(Arrays.asList(m,m2));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1= new Pedido(null,sdf.parse("30/09/2017 10:32"),m,u2,u);
        ItemPedido i = new ItemPedido(ped1,p1,1);
        ItemPedido i2 = new ItemPedido(ped1,p2,2);
        ped1.getItens().add(i);
        ped1.getItens().add(i2);
        ped1.calculaTotal();

        Pedido ped2= new Pedido(null,sdf.parse("01/05/2019 11:12"),m2,u3,u2);
        ItemPedido i3 = new ItemPedido(ped2,p1,5);
        ItemPedido i4 = new ItemPedido(ped2,p2,5);
        ped2.getItens().add(i3);
        ped2.getItens().add(i4);
        ped2.calculaTotal();


        pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
    }
}
