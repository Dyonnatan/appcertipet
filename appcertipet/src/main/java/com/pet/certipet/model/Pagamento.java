package com.pet.certipet.model;

public enum Pagamento {
	P('p',"Pago"), A('a',"Aguardando"), R('r',"Recusado"), C('c',"Cancelado");
	
	String pagamento;
	char pag;
	
	private Pagamento(char pag, String pagamento) {
		this.pag = pag;
		this.pagamento=pagamento;
	}
	private Pagamento(char pag) {
		this.pag = pag;
	}
	private Pagamento(String pagamento) {
		this.pagamento=pagamento;
	}
	
	public String getPagamento() {
		return pagamento;
	}
	
	public char getChar() {
		return pag;
	}
	
}
