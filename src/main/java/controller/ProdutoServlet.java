package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Marca;
import model.Produto;
import persistence.GenericDao;
import persistence.IMarcaDao;
import persistence.IProdutoDao;
import persistence.MarcaDao;
import persistence.ProdutoDao;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ProdutoServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GenericDao gDao = new GenericDao();
		IMarcaDao mDao = new MarcaDao(gDao);
		String erro = "";
		List<Marca> marcas = new ArrayList<Marca>();
		
		try {
			marcas = mDao.consultaMarcas();
		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("produto.jsp");
			request.setAttribute("marcas", marcas);
			request.setAttribute("erro", erro);
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String preco = request.getParameter("preco");
		String marca = request.getParameter("marca");
		String botao = request.getParameter("botao");
		
		Produto p = new Produto();
		Marca m = new Marca();
		
		GenericDao gDao = new GenericDao();
		IProdutoDao pDao = new ProdutoDao(gDao);
		IMarcaDao mDao = new MarcaDao(gDao);
		String saida = "";
		String erro = "";
		List<Produto> produtos = new ArrayList<Produto>();
		List<Marca> marcas = new ArrayList<Marca>();
		
		try {
			if (botao.equals("Listar")) {
				produtos = pDao.consultaProdutos();
			}
			if (botao.equals("Inserir")) {
				p = valido(id, nome, preco, marca, botao);
				saida = pDao.insereProduto(p);
				p = new Produto();
			}
			if (botao.equals("Atualizar")) {
				p = valido(id, nome, preco, marca, botao);
				saida = pDao.atualizaProduto(p);
				p = new Produto();
			}
			if (botao.equals("Excluir")) {
				p = valido(id, nome, preco, marca, botao);
				saida = pDao.excluiProduto(p);
				p = new Produto();
			}
			if (botao.equals("Buscar")) {
				p = valido(id, nome, preco, marca, botao);
				p = pDao.consultaProduto(p);
				m = p.getMarca();
			}
		} catch (SQLException | ClassNotFoundException | IOException e) {
			erro = e.getMessage();
		} finally {
			try {
				marcas = mDao.consultaMarcas();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			RequestDispatcher rd = request.getRequestDispatcher("produto.jsp");
			request.setAttribute("produto", p);
			request.setAttribute("produtos", produtos);
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			request.setAttribute("marcas", marcas);
			request.setAttribute("marca", m);
			rd.forward(request, response);
		}
	}
	
	private Produto valido(String id, String nome, String preco, String marca, String botao)
		throws IOException {
		Produto p = new Produto();
		Marca m = new Marca();
		
		if (botao.equals("Inserir")) {
			if (id.equals("") || nome.equals("") || preco.equals("") || marca.equals("0")) {
				throw new IOException("Preencher os campos");
			} else {
				m.setId(Integer.parseInt(marca));
				
				p.setId(Integer.parseInt(id));
				p.setNome(nome);
				p.setPreco(Float.parseFloat(preco));
				p.setMarca(m);
			}
		}
		if (botao.equals("Atualizar")) {
			if (id.equals("") || nome.equals("") || preco.equals("") || marca.equals("0")) {
				throw new IOException("Preencher os campos");
			} else {
				m.setId(Integer.parseInt(marca));
				
				p.setId(Integer.parseInt(id));
				p.setNome(nome);
				p.setPreco(Float.parseFloat(preco));
				p.setMarca(m);
			}
		}
		if (botao.equals("Excluir")) {
			if (id.equals("")) {
				throw new IOException("Preencher o ID");
			} else {
				p.setId(Integer.parseInt(id));
			}
		}
		if (botao.equals("Buscar")) {
			if (id.equals("")) {
				throw new IOException("Preencher o ID");
			} else {
				p.setId(Integer.parseInt(id));
			}
		}
		
		return p;
	}

}
