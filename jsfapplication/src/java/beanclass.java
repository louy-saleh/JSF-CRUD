import java.sql.*; 
import java.sql.Connection; 
import java.sql.DriverManager;   
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.ReferencedBean;  
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.RequestScoped;  
@ManagedBean  
@RequestScoped  
public class beanclass {  
String name;  
  
public String getName() {  
return name;  
}  
public void setName(String name) {  
this.name = name;  
}  
public boolean save(){  
boolean result = false;  

try{ 
     
 Class.forName ("com.mysql.jdbc.Driver");
    
Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/dbase","root","1234");  
 Statement stmt = con.createStatement();  
stmt.execute("insert INTO user( firstName) values(' " + this.getName()+" ')");
result=true;
}catch(Exception e){  
   result=false;
    setName(e+"");
}  
if(result == true){  
return true;  
}else return false;  
}
public String submit(){  
if(this.save()){  
return "response.xhtml";  
}else return "index.xhtml";  
} 

/*

        Deleting user by firstname

public boolean delete(){  
boolean result = false;  

try{ 
     
 Class.forName ("com.mysql.jdbc.Driver");
    
Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/dbase","root","1234");  
 Statement stmt = con.createStatement();  
stmt.execute("delete FROM user( firstName) values(' " + this.getName()+" ')");
result=true;
}catch(Exception e){  
   result=false;
    setName(e+"");
}  
if(result == true){  
return true;  
}else return false;  
} */
  

}  