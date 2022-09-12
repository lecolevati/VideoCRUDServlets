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
import persistence.GenericDao;
import persistence.IMarcaDao;
import persistence.MarcaDao;

@WebServlet("/marca")
public class MarcaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MarcaServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String botao = request.getParameter("botao");
		
		Marca m = new Marca();
		
		GenericDao gDao = new GenericDao();
		IMarcaDao mDao = new MarcaDao(gDao);
		String erro = "";
		String saida = "";
		List<Marca> marcas = new ArrayList<Marca>();
		
		try {
			if (botao.equals("Listar")) {
				marcas = mDao.consultaMarcas();
			}
			if (botao.equals("Inserir")) {
				m = valido(id, nome, botao);
				saida = mDao.insereMarca(m);
				m = new Marca();
			}
			if (botao.equals("Atualizar")) {
				m = valido(id, nome, botao);
				saida = mDao.atualizaMarca(m);
				m = new Marca();
			}
			if (botao.equals("Excluir")) {
				m = valido(id, nome, botao);
				saida = mDao.excluiMarca(m);
				m = new Marca();
			}
			if (botao.equals("Buscar")) {
				m = valido(id, nome, botao);
				m = mDao.consultaMarca(m);
			}
		} catch(SQLException | ClassNotFoundException | IOException e) {
			erro = e.getMessage();
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("marca.jsp");
			request.setAttribute("marca", m);
			request.setAttribute("marcas", marcas);
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			rd.forward(request, response);
		}
	}
	
	private Marca valido(String id, String nome, String botao) throws IOException {
		Marca m = new Marca();
		
		if (botao.equals("Inserir")) {
			if (id.equals("") || nome.equals("")) {
				throw new IOException("Preencher ID e Nome");
			} else {
				m.setId(Integer.parseInt(id));
				m.setNome(nome);
			}
		}
		if (botao.equals("Atualizar")) {
			if (id.equals("") || nome.equals("")) {
				throw new IOException("Preencher ID e Nome");
			} else {
				m.setId(Integer.parseInt(id));
				m.setNome(nome);
			}
		}
		if (botao.equals("Excluir")) {
			if (id.equals("")) {
				throw new IOException("Preencher ID");
			} else {
				m.setId(Integer.parseInt(id));
			}
		}
		if (botao.equals("Buscar")) {
			if (id.equals("")) {
				throw new IOException("Preencher ID");
			} else {
				m.setId(Integer.parseInt(id));
			}
		}

		return m;
	}

}
