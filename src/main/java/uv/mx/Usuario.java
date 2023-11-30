package uv.mx;

public class Usuario {
    private String id;
    private String nombre;
    private String password;

    // constructors, getters and setters
    public Usuario(String id, String nombre, String password){
        this.id = id;
        this.nombre = nombre;
        this.password = password;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getNombre(){
        return this.nombre;
    }

    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nombre=" + nombre + ", password=" + password + "]";
    }
    
}