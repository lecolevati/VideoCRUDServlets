<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="./css/styles.css">
<title>Produto</title>
</head>
<body>
	<div>
		<jsp:include page="menu.jsp" />
	</div>
	<br />
		<div align="center" class="container">
		<form action="produto" method="post">
			<p class="title">
				<b>Produtos</b>
			</p>
			<table>
				<tr>
					<td colspan="3">
						<input class="id_input_data" type="number" min="0"
							step="1" id="id" name="id" placeholder="#ID"
							value='<c:out value="${produto.id }"></c:out>'>
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Buscar">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text" id="nome" name="nome"
							placeholder="Nome"
							value='<c:out value="${produto.nome }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="number" min="0"
							step="0.01" id="preco" name="preco" placeholder="Preço"
							value='<c:out value="${produto.preco }"></c:out>'>
					</td>				
				</tr>
				<tr>
					<td colspan="4">
						<select class="input_data" id="marca" name="marca">
							<option value="0">Selecione uma Marca...</option>
							<c:if test="${not empty marcas }">
								<c:forEach items="${marcas }" var="m">
									<c:if test="${not empty marca }">
										<c:if test="${m.id eq marca.id }">
											<option value="${m.id }" selected="selected">
												<c:out value="${m.nome }" />
											</option>
										</c:if>
									</c:if>
									<c:if test="${(empty marca) || (m.id ne marca.id) }">
										<option value="${m.id }">
											<c:out value="${m.nome }" />
										</option>
									</c:if>
								</c:forEach>
							</c:if>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Inserir">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Atualizar">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Excluir">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Listar">
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<div align="center">
		<c:if test="${not empty erro }">
			<H2><c:out value="${erro }" /></H2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida }">
			<H3><c:out value="${saida }" /></H3>
		</c:if>
	</div>	
	<br />
	<br />
	<div align="center">
		<c:if test="${not empty produtos }">
			<table class="table_round">
				<thead>
					<tr>
						<th><b>#ID</b></th>
						<th><b>Nome</b></th>
						<th><b>Preço</b></th>
						<th><b>Marca</b></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${produtos }" var="p">
					<tr>
						<td><c:out value="${p.id }" /></td>
						<td><c:out value="${p.nome }" /></td>
						<td><c:out value="${p.preco }" /></td>
						<td><c:out value="${p.marca.nome }" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>