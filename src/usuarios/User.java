package usuarios;

public class User {

	protected String nombre;
	protected String apellidos;
	//protected String imagen;
	protected byte[] imagen;
	protected String password;
	protected String email;
	protected String poblacion;
	
	public User(String nombre, String apellidos, byte[] imagen, String email, String poblacion) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.imagen = imagen;
		this.email = email;
		this.poblacion = poblacion;
	}

	public User(String nombre, String apellidos, byte[] imagen, String password, String email, String poblacion) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.imagen = imagen;
		this.password = password;
		this.email = email;
		this.poblacion = poblacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	
	

}
