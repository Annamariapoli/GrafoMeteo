package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bean.Situazione;

public class Dao {
	
	public List<Situazione> getSituazioni(){     //nodi grafo
		Connection conn = DBConnect.getConnection();
		String query = "select * from situazione where Localita = 'Torino' and data >= '2013/01/01' and data <= '2013/12/31' order by data ASC;";
		try{
			PreparedStatement st = conn.prepareStatement(query);
			List<Situazione> nodi = new LinkedList<Situazione>();
			ResultSet res = st.executeQuery();
			while(res.next()){
				Situazione s = new Situazione(res.getString("localita"), res.getDate("date"), res.getInt("tmedia"), res.getInt("tmin"), res.getInt("tmax"),
						res.getInt("puntorugiada"), res.getInt("umidita"), res.getInt("visibilita"), res.getInt("ventomedia"), res.getInt("ventomax"), res.getInt("raffica"),
						res.getInt("pressioneslm"), res.getInt("pressionemedia"), res.getInt("pioggia"), res.getString("fenomeni"));
			    nodi.add(s);
			}
			conn.close();
			return nodi;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}

}
