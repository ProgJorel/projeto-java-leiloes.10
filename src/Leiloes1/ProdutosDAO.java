package Leiloes1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    // Método para cadastrar um produto no banco de dados
    public void cadastrarProduto(String nomeProduto, double valorProduto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        
        // Usamos um bloco try-with-resources para garantir que os recursos sejam fechados automaticamente
        try (Connection con = new conectaDAO().connectDB(); // Estabelece a conexão com o banco de dados
             PreparedStatement pst = con.prepareStatement(sql)) { // Prepara a consulta SQL para execução

            // Preenche os parâmetros da consulta com os valores recebidos da interface gráfica
            pst.setString(1, nomeProduto); // Nome do produto
            pst.setDouble(2, valorProduto); // Valor do produto
            pst.setString(3, "A venda"); // Status inicial do produto (Disponível)

            // Executa a consulta SQL para inserir o produto no banco de dados
            pst.executeUpdate();

            // Exibe uma mensagem de sucesso para o usuário
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

            

        } catch (SQLException e) { // Captura exceções SQL
            // Exibe uma mensagem de erro caso algo dê errado na operação
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    // Método para listar todos os produtos disponíveis no banco de dados
   public List<ProdutosDTO> listarProdutos() {
    List<ProdutosDTO> listagem = new ArrayList<>();
    String sql = "SELECT id, nome, valor, status FROM produtos";

    try (Connection con = new conectaDAO().connectDB();
         PreparedStatement pst = con.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));
            listagem.add(produto);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
    }

    return listagem;
}
    
    
    // Método para listar apenas os produtos vendidos
    public List<Object[]> listarProdutosVendidos() {
        List<Object[]> produtosVendidos = new ArrayList<>();
        String sql = "SELECT id, nome, valor, status FROM produtos WHERE status = 'Vendido'";

        try (Connection con = new conectaDAO().connectDB();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                produtosVendidos.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("valor"),
                    rs.getString("status")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + e.getMessage());
        }

        return produtosVendidos;
    }
   
   // Método para atualizar o modelo da tabela com produtos
    public void atualizarTabelaProdutos(DefaultTableModel modelo) {
        try {
            // Cria uma instância de ProdutosDAO para interagir com o banco de dados
            List<ProdutosDTO> produtos = listarProdutos();

            // Limpa todas as linhas existentes na tabela para evitar duplicação de dados
            modelo.setRowCount(0);

            // Adiciona cada produto como uma nova linha na tabela
            for (ProdutosDTO produto : produtos) {
                modelo.addRow(new Object[]{
                    produto.getId(),    // ID do produto
                    produto.getNome(),  // Nome do produto
                    produto.getValor(), // Valor do produto
                    produto.getStatus() // Status do produto (ex: "Disponível" ou "Vendido")
                });
            }
        } catch (Exception e) {
            // Exibe uma mensagem de erro caso ocorra algum problema ao atualizar a tabela
            JOptionPane.showMessageDialog(null, "Erro ao atualizar tabela: " + e.getMessage());
        }
    }  
    
    public void venderProduto(int idProduto) {
    String sql = "UPDATE produtos SET status = ? WHERE id = ?";
    
    try (Connection con = new conectaDAO().connectDB();
         PreparedStatement pst = con.prepareStatement(sql)) {
        
        pst.setString(1, "Vendido");
        pst.setInt(2, idProduto);
        
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
    }
}
    
        
}

