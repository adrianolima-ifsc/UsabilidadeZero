package models;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Validate;

@Validate
public class Inscricao {
	
	private Long tarefa;
	private Long evento;
	private String nome;
	private String email;
	private String fone;
	private String cpf;
	private String endereco;
	private String cidade;
	private String numCartao;
	private String titularCartao;
	private String validade;
	private String codigoSeguranca;
	private boolean valor;

	public Long getTarefa() {
		return tarefa;
	}

	public void setTarefa(Long tarefa) {
		this.tarefa = tarefa;
	}

	public Long getEvento() {
		return evento;
	}

	public void setEvento(Long evento) {
		this.evento = evento;
	}

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFone() {
		return fone;
	}
	
	public void setFone(String fone) {
		this.fone = fone;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public String getNumCartao() {
		return numCartao;
	}
	
	public void setNumCartao(String numCartao) {
		this.numCartao = numCartao;
	}
	
	public String getTitularCartao() {
		return titularCartao;
	}
	
	public void setTitularCartao(String titularCartao) {
		this.titularCartao = titularCartao;
	}
	
	public String getValidade() {
		return validade;
	}
	
	public void setValidade(String validade) {
		this.validade = validade;
	}
	
	public String getCodigoSeguranca() {
		return codigoSeguranca;
	}
	
	public void setCodigoSeguranca(String codigoSeguranca) {
		this.codigoSeguranca = codigoSeguranca;
	}
	
	public boolean isValor() {
		return valor;
	}

	public void setValor(boolean valor) {
		this.valor = valor;
	}
}
