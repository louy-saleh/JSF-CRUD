/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.Statement;  
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.util.ArrayList;  
import java.util.Map;  
import javax.faces.bean.ManagedBean;  
import javax.faces.bean.RequestScoped;  
import javax.faces.context.FacesContext;  
/**
 *
 * @author LOUY-PC
 */
@ManagedBean  
@RequestScoped  
public class User {
    int id;
    String firstName;
    String lastName;
    ArrayList usersList;
    private Map<String,Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    Connection connection;
    
    
    public int getId(){
        return id;
    }
    public String getfirstName(){
        return firstName;
    }
    public String getlastName(){
        return lastName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    // Used to establish connection
    public Connection getConnection(){
        try{  
            Class.forName("com.mysql.jdbc.Driver");     
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/dbase","root","1234");  
            }catch(Exception e){  
            System.out.println(e);  
            }  
            return connection;  
    }  
    
    //Used To fetch all records
    public ArrayList userList() throws SQLException{
        try{
            usersList=new ArrayList();
            connection = getConnection();
            Statement stmt=getConnection().createStatement();
            ResultSet rs=stmt.executeQuery("select * from user");    
            while(rs.next()){  
            User user = new User();
            user.setId(rs.getInt("id"));  
            user.setFirstName(rs.getString("firstName"));  
            user.setLastName(rs.getString("lastName"));
            usersList.add(user);
            }
            //closing connection
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return usersList;
    }
    //Used to Save user Record
    public String save() throws SQLException{
        int result=0;
        try{
            connection=getConnection();
            PreparedStatement stmt = connection.prepareStatement("insert into user(firstName,lastName) values(?,?)");
            stmt.setString(1,firstName);
            stmt.setString(2,lastName);
            result=stmt.executeUpdate(); 
            connection.close();
        }catch(Exception e){
            System.out.println(e);
        }
        if(result !=0)
            return "index.xhtml?faces-redirect=true";
        else return "create.xhtml?faces-redirect=true";
    }
    
    // Used to fetch record to update  
    public String edit(int id){  
    User user = null;  
    System.out.println(id);  
    try{  
    connection = getConnection();  
    Statement stmt=getConnection().createStatement();    
    ResultSet rs=stmt.executeQuery("select * from user where id = "+(id));
    rs.next();
    user = new User();
    user.setId(rs.getInt("id"));
    user.setFirstName(rs.getString("firstName"));
    user.setLastName(rs.getString("lastName"));
    sessionMap.put("editUser", user);  
    connection.close();
    }catch(Exception e){  
    System.out.println(e);
}
    return "/edit.xhtml?faces-redirect=true";
}
    
    //Used to update user record
    public String update(User u){
        try{  
            connection = getConnection();    
            PreparedStatement stmt=connection.prepareStatement(  
            "update user set firstName=?,lastName=? where id=?");    
            stmt.setString(1,u.getfirstName());    
            stmt.setString(2,u.getlastName());    
            stmt.setInt(3,u.getId());    
            stmt.executeUpdate();  
            connection.close();
            }catch(Exception e){  
         System.out.println(e);  
        }
        return "/index.xhtml?faces-redirect=true";
    }
    
    
    // Used to delete user record  
    public void delete(int id){  
    try{  
    connection = getConnection();    
    PreparedStatement stmt = connection.prepareStatement("delete from user where id = "+id);    
    stmt.executeUpdate();    
    }catch(Exception e){  
        System.out.println(e);  
        }  
    } 
}