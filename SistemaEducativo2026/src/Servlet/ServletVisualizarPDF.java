package Servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Util.Conexion;

 
@WebServlet("/VisualizarPDF")
public class ServletVisualizarPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ServletVisualizarPDF() {
        super();
         
    }

 
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		response.setContentType("application/pdf");
		
		
		String codigo = request.getParameter("codigo");
		byte[] b = null;
		
		String sql = "select archivo from sesion_aprendizaje where idSesion = '"+codigo+"';";
		
		try
		{
			Conexion con = new Conexion();
			PreparedStatement ps = null;
			ps = con.executePreparedStatement(sql);
			ResultSet rs = ps.executeQuery();
			
		
			while(rs.next()) {

				b = rs.getBytes(1);
			}
			
			InputStream bos = new ByteArrayInputStream(b);
			
			int tamanoInput = bos.available();
			byte[] datosPDF = new byte[tamanoInput];
			bos.read(datosPDF, 0 , tamanoInput);
			
			response.getOutputStream().write(datosPDF);
			bos.close();
			ps.close();
			rs.close();
			
			
		} catch(Exception e) {
			
			System.out.println(e.getMessage());
		}
		
	}

}
