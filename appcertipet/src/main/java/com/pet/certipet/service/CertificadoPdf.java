package com.pet.certipet.service;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.BaseDirection;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.pet.certipet.model.CampoChave;
import com.pet.certipet.model.Certificado;
import com.pet.certipet.model.Evento;
import com.pet.certipet.model.Participante;
import com.pet.certipet.model.TipoParticipante;

public class CertificadoPdf {

	private byte[] imagemBackground;
	private float xConteudo = 110;
	private float yConteudo = 180;
	private float wConteudo = 530;
	private float xData = 110;
	private float yData = 250;
	private float wData = 530;
	private boolean hasData = true;
	private float xAutentica = 0;
	private float yAutentica = 5;
	private boolean hasAutentica = true;
	private static Style textStyle = new Style();
	private static Style fieldStyle = new Style();

	public CertificadoPdf() {
		try {
			PdfFont fonte = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
			textStyle.setFont(fonte).setFontSize(18);
			PdfFont fonte2 = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
			fieldStyle.setFont(fonte2).setFontSize(18);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFontStyle(String font, float size) throws IOException {
		PdfFont fonte = PdfFontFactory.createFont(font);
		textStyle.setFont(fonte).setFontSize(size);
		if (FontConstants.TIMES_ROMAN.equals(font)) {
			font = "Times";
		} else if (FontConstants.SYMBOL.equals(font)) {
			fieldStyle.setFont(fonte).setFontSize(size);
			return;
		}
		PdfFont fonte2 = PdfFontFactory.createFont(font + "-Bold");
		fieldStyle.setFont(fonte2).setFontSize(size);
	}

	public void setFontSameStyle(String font, float size) throws IOException {
		PdfFont fonte = PdfFontFactory.createFont(font);
		textStyle.setFont(fonte).setFontSize(size);
		fieldStyle.setFont(fonte).setFontSize(size);
	}

	public boolean personalizarPlane(Certificado cert, Participante part, Evento even, TipoParticipante tPar) {
		String texto = tPar.getTextoCertificado();
		Paragraph p = new Paragraph();
		boolean continua = true;
		while (continua)
			for (CampoChave cc : CampoChave.values()) {
				continua = false;
				switch (cc) {
				case NOME:
					String tnome[] = texto.split(cc.getChave(), 2);
					p.add(tnome[0]);
					p.add(new Text(part.getNome() + " " + part.getSobrenome()));
					continua = true;
					break;
				case EVENTO:
					texto.replaceAll(cc.getChave(), even.getNome());
					continua = true;
					break;
				case DATA_EVENTO:
					texto.replaceAll(cc.getChave(), setData(even.getDataRealizacao()));
					continua = true;
					break;
				case DATA_EVENTO_INGLES:
					texto.replaceAll(cc.getChave(), setDate(even.getDataRealizacao()));
					continua = true;
					break;
				case MATRICULA:
					texto.replaceAll(cc.getChave(), part.getMatricula());
					continua = true;
					break;
				case DATA_ATUAL:
					DateTimeFormatter f = DateTimeFormatter.ofPattern("'Goiânia,' dd 'de' MMMM 'de' yyyy",
							new Locale("pt", "br"));
					texto.replaceAll(cc.getChave(), f.format(Instant.now()));
					continua = true;
					break;

				default:
					break;
				}
			}

		return true;
	}

	private String setData(Date realizacao) {
		String data = "";
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy'.'", new Locale("pt", "br"));
			data = format.format(realizacao);
		} catch (Exception e) {
		}
		return data;
	}

	private String setDate(Date realizacao) {
		String data = "";
		try {
			Locale ingles = new Locale("en");
			SimpleDateFormat format = new SimpleDateFormat("MMMM dd',' yyyy", ingles);
			data = format.format(realizacao);
		} catch (Exception e) {
		}
		return data;
	}

	public java.io.ByteArrayOutputStream gerar(String titulo, Certificado cert, List<Paragraph> paragrafo)
			throws IOException {

		ByteArrayOutputStream documento = new ByteArrayOutputStream();
		// Initialize PDF writer
		PdfWriter writer = new PdfWriter(documento);

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		pdf.getDocumentInfo().setTitle(titulo);
		pdf.getDocumentInfo().setAuthor("AppCertipet PET-EMC UFG");

		// Initialize document
		PageSize pageSize = PageSize.A4.rotate();
		pdf.setDefaultPageSize(pageSize);
		Document document = new Document(pdf, pageSize);

		// Imagem de backgroung
		if (cert.getArquivo() != null) {
			setImagemBackground(cert.getArquivo());
		}

		Image image1 = new Image(ImageDataFactory.create(getImagemBackground()));
		image1.setFixedPosition(0f, 0f, document.getWidth());
		image1.scaleAbsolute(pageSize.getWidth(), pageSize.getHeight());
		document.add(image1);

		Table t = new Table(1);

		t.setTextAlignment(TextAlignment.CENTER);
		Cell c = new Cell(1, 1);
		// c.setBorder(null);
		c.setVerticalAlignment(VerticalAlignment.TOP);
		c.setHorizontalAlignment(HorizontalAlignment.CENTER);
		Paragraph conteudo = paragrafo.get(0);
		// conteudo.setRelativePosition(xConteudo - wConteudo + 100, yConteudo,
		// wConteudo + 100, yConteudo + wConteudo);
		// FixedPosition(xConteudo, yConteudo, wConteudo);
		conteudo.setRelativePosition(xConteudo, yConteudo, 0, 0);
		conteudo.setWidth(wConteudo);
		conteudo.setFirstLineIndent(40f);
		conteudo.setTextAlignment(TextAlignment.JUSTIFIED);
		c.add(conteudo);
		t.addCell(c);
		document.add(conteudo);

		if (paragrafo.size() >= 2) {
			Paragraph data = paragrafo.get(1);
			// wData = wConteudo+xConteudo-xData
			// data.setFixedPosition(xData, yData, wConteudo + xConteudo -
			// xData);
			data.setWidth(wData);
			data.setRelativePosition(xData, yData, yData + wData, 0);
			data.setTextAlignment(TextAlignment.RIGHT);
			document.add(data);
		}
		if (paragrafo.size() >= 3) {
			Paragraph autenticacao = paragrafo.get(2);
			autenticacao.setFixedPosition(xAutentica, yAutentica, pageSize.getWidth());
			autenticacao.setTextAlignment(TextAlignment.CENTER);
			autenticacao.setFontSize(9);
			autenticacao.setFontColor(com.itextpdf.kernel.color.Color.DARK_GRAY);
			document.add(autenticacao);
		}
		// Close document
		document.close();
		return documento;
	}

	public byte[] getImagemBackground() {
		return imagemBackground;
	}

	public void setImagemBackground(byte[] imagemBackground) {
		this.imagemBackground = imagemBackground;
	}

	public float getxConteudo() {
		return xConteudo;
	}

	public void setxConteudo(float xConteudo) {
		this.xConteudo = xConteudo;
	}

	public float getyConteudo() {
		return yConteudo;
	}

	public void setyConteudo(float yConteudo) {
		this.yConteudo = yConteudo;
	}

	public float getwConteudo() {
		return wConteudo;
	}

	public void setwConteudo(float wConteudo) {
		this.wConteudo = wConteudo;
	}

	public float getxData() {
		return xData;
	}

	public void setxData(float xData) {
		this.xData = xData;
	}

	public float getyData() {
		return yData;
	}

	public void setyData(float yData) {
		this.yData = yData;
	}

	public float getwData() {
		return wData;
	}

	public void setwData(float wData) {
		this.wData = wData;
	}

	public float getxAutentica() {
		return xAutentica;
	}

	public void setxAutentica(float xAutentica) {
		this.xAutentica = xAutentica;
	}

	public float getyAutentica() {
		return yAutentica;
	}

	public void setyAutentica(float yAutentica) {
		this.yAutentica = yAutentica;
	}

	// public static void main(String[] args) throws IOException {
	// CertificadoPdf cp = new CertificadoPdf();
	// cp.gerar("");
	//
	// }

	public List<Paragraph> formatar(String autenticacao, Participante part, Evento even, TipoParticipante tPar)
			throws IOException {
		String texto = tPar.getTextoCertificado();
		String texto2 = "Certificamos que @nome@ participou \ndo @evento@ no dia @data@ para todos os fins @@"
				+ " @gerar_data@{232.2, -212.2} @posic@";
		boolean portugues = true;
		String sp = "|";
		String op = CampoChave.NOME.getChave() + sp + CampoChave.EVENTO.getChave() + sp
				+ CampoChave.CARGA_HORARIA.getChave() + sp + CampoChave.DATA_EVENTO.getChave() + sp
				+ CampoChave.DATA_EVENTO_INGLES.getChave() + sp + CampoChave.MATRICULA.getChave() + sp
				+ CampoChave.CPF.getChave();
		String regex = "(.*?)(" + op + ")(.+)";
		Pattern patt = Pattern.compile(regex, Pattern.DOTALL);
		Paragraph p = new Paragraph();
		while (true) {
			Matcher m = patt.matcher(texto);
			if (m.find()) {
				p.add(new Text(m.group(1)).addStyle(textStyle));
				String param = m.group(2);
				String campo = "";
				if (param.equals(CampoChave.NOME.getChave())) {
					campo = part.getNome() + " " + part.getSobrenome();
				} else if (param.equals(CampoChave.EVENTO.getChave())) {
					campo = even.getNome();
				} else if (param.equals(CampoChave.DATA_EVENTO.getChave())) {
					campo = setData(even.getDataRealizacao());
				} else if (param.equals(CampoChave.CARGA_HORARIA.getChave())) {
					campo = tPar.getCargaHoraria();
				} else if (param.equals(CampoChave.MATRICULA.getChave())) {
					campo = part.getMatricula();
				} else if (param.equals(CampoChave.CPF.getChave())) {
					campo = part.getCpf();
				} else if (param.equals(CampoChave.DATA_EVENTO_INGLES.getChave())) {
					campo = setDate(even.getDataRealizacao());
					portugues = false;
				}
				p.add(campo).addStyle(fieldStyle);
			} else {
				break;
			}
			texto = m.group(3);
		}

		patt = Pattern.compile("(.*?)@@(.*)", Pattern.DOTALL);
		Matcher m = patt.matcher(texto);
		if (!m.find()) {
			p.add(new Text(texto).addStyle(textStyle));
		} else {
			p.add(new Text(m.group(1)).addStyle(textStyle));
			String op2 = CampoChave.DATA_ATUAL.getChave() + sp + CampoChave.AUTENTICA.getChave() + sp
					+ CampoChave.PARAGRAFO.getChave() + sp + CampoChave.WIDTH.getChave() + sp
					+ CampoChave.FONTE.getChave();
			patt = Pattern.compile("(" + op2 + ")(\\{\\s?(.+?),\\s?(.*?)\\})?", Pattern.MULTILINE);
			m = patt.matcher(texto);
			while (m.find()) {
				setVariavel(m.group(1), m.group(3), m.group(4));
			}
		}

		List<Paragraph> retorno = new LinkedList<>();
		retorno.add(0, p);
		String dataAtualStr = "";
		if (hasData) {
			if (portugues) {
				dataAtualStr = "Goiânia, " + setData(new Date());
			} else {
				dataAtualStr = "Goiânia, " + setDate(new Date());
			}
		}
		Paragraph dataAtual = new Paragraph(dataAtualStr);
		dataAtual.addStyle(textStyle);
		retorno.add(1, dataAtual);

		if (hasAutentica) {
			String autenticaStr;
			if (portugues)
				autenticaStr = "A autenticidade deste documento pode ser verificada por meio da URL: ";
			else
				autenticaStr = "The authenticity of this document can be verified by URL: ";

			Link autenticacaoLink = new Link(autenticacao, PdfAction.createURI(autenticacao));
			Paragraph autentParag = new Paragraph();
			autentParag.add(autenticaStr);
			autentParag.add(autenticacaoLink);
			retorno.add(2, autentParag);
		}

		return retorno;
	}

	private void setVariavel(String variavel, String v, String v3) throws IOException {

		if (CampoChave.FONTE.getChave().equals(variavel)) {
			float v2 = getyConteudo();
			try {
				v2 = Float.parseFloat(v3);
			} catch (NumberFormatException e) {
			}
			if ("Courier Helvetica Symbol Times-Roman".matches(v)) {
				setFontStyle(v, v2);
			}
		} else if (CampoChave.DATA_ATUAL.getChave().equals(variavel)) {
			float v1 = getxData();
			float v2 = getyData();
			try {
				v1 = Float.parseFloat(v);
				v2 = Float.parseFloat(v3);
				if (v1 < 0L || v2 < 0L) {
					hasData = false;
				}
			} catch (NumberFormatException e) {
			}
			setxData(v1);
			setyData(v2);
		} else if (CampoChave.AUTENTICA.getChave().equals(variavel)) {
			float v1 = getxAutentica();
			float v2 = getyAutentica();
			try {
				v1 = Float.parseFloat(v);
				v2 = Float.parseFloat(v3);
				if (v1 < 0L || v2 < 0L) {
					hasAutentica = false;
				}
			} catch (NumberFormatException e) {
			}
			setxAutentica(v1);
			setyAutentica(v2);
		} else if (CampoChave.PARAGRAFO.getChave().equals(variavel)) {
			float v1 = getxConteudo();
			float v2 = getyConteudo();
			try {
				v1 = Float.parseFloat(v);
				v2 = Float.parseFloat(v3);
			} catch (NumberFormatException e) {
			}
			setxConteudo(v1);
			setyConteudo(v2);
		} else if (CampoChave.WIDTH.getChave().equals(variavel)) {
			float v1 = getwConteudo();
			float v2 = getwData();
			try {
				v1 = Float.parseFloat(v);
			} catch (NumberFormatException e) {
			}
			try {
				v2 = Float.parseFloat(v3);
			} catch (NumberFormatException e) {
			}
			if (v1 >= 0)
				setwConteudo(v1);
			if (v2 >= 0)
				setwData(v2);
		}
	}
}
