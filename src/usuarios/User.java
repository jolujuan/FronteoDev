package usuarios;

public class User {

	protected String nombre;
	protected String apellidos;
	protected String password;
	protected String email;
	protected String poblacio;
	
	public User(String nombre, String apellidos, String email, String poblacio) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.poblacio = poblacio;
	}
	
	public User(String nombre, String apellidos, String password, String email, String poblacio) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.password = password;
		this.email = email;
		this.poblacio = poblacio;
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

	public String getPoblacio() {
		return poblacio;
	}

	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}
	
	
	
}
