package Leiloes1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
public class conectaDAO {
    
    public Connection connectDB(){
        Connection conn = null;
        
       try {
            
            // Melhorando a URL de conexão com configurações adicionais de SSL
            String url = "jdbc:mysql://localhost:3306/uc11"
                       + "?user=root"
                       + "&password=123456"
                       + "&useSSL=false";
                      
            // Estabelecendo a conexão com o banco de dados
            conn = DriverManager.getConnection(url);
            
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + erro.getMessage());
        }
        return conn;
    }
    
}
