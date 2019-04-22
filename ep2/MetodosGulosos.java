package ep2;

import java.util.Comparator;
import java.util.Stack;

/*********************************************************************/
/** ACH 2002 - Introducao a Ciencia da Computacao II                **/
/** EACH-USP - Segundo Semestre de 2010                             **/
/**                                                                 **/
/** <turma> - <nome do professor>                                   **/
/**                                                                 **/
/** Terceiro Exercicio-Programa                                     **/
/**                                                                 **/
/** <nome do(a) aluno(a)> <numero USP>                              **/
/**                                                                 **/
/*********************************************************************/

/**
 * COMENTARIOS GERAIS
 * 
 * Seguindo os criterios de selecao, um objeto só poderá ser colocado na mochila
 * caso ela suporte o total de peso.
 * 
 * O total de peso ao se colocar um objeto (do tipo Objeto) é dado por
 * mochila.getPesoUsado() + objeto.getPeso()
 * 
 * Colocar um objeto na mochila significa alterar os seguintes campos da
 * mochila:
 * 
 * pesoUsado,
 * 
 * valorDentroDaMochila, e
 * 
 * numObjetosNaMochila.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class MetodosGulosos {

    /**
     * Este método deve implementar um algoritmo guloso que selecione objetos da
     * listaDeObjetosDisponiveis a serem colocados na mochila, de acordo com o
     * critério 'objetos de menor peso primeiro'. Caso dois objetos tenham o mesmo
     * peso, o critério de desempate será 'objetos de maior valor primeiro' (apenas
     * para os empates em peso).
     * 
     * @param pesoMaximoDaMochila
     *                                      Peso máximo suportado pela mochila
     * @param listaDeObjetosDisponiveis
     *                                      Arranjo de objetos considerados no
     *                                      problema
     * 
     * @return Mochila carregada conforme essa estratégia
     */
    public static Mochila utilizaMenorPeso(double pesoMaximoDaMochila, Objeto[] listaDeObjetosDisponiveis) {
	Stack<Objeto> lista = construirPilha(listaDeObjetosDisponiveis);
	lista.sort(compararPorPeso());
	Mochila mochila = new Mochila(pesoMaximoDaMochila);
	while (!lista.empty()) {
	    Objeto item = lista.pop();
	    if (validarPesoMochila(mochila, item)) {
		mochila.setPesoUsado(mochila.getPesoUsado() + item.getPeso());
		mochila.setValorDentroDaMochila(mochila.getValorDentroDaMochila() + item.getValor());
		mochila.setNumObjetosNaMochila(mochila.getNumObjetosNaMochila() + 1);
	    }
	}

	return mochila;
    }

    /**
     * Este método deve implementar um algoritmo guloso que selecione objetos da
     * listaDeObjetosDisponiveis a serem colocados na mochila, de acordo com o
     * critério 'objetos de maior valor primeiro'. Caso dois objetos tenham o mesmo
     * valor, o critério de desempate sera 'objetos de menor peso primeiro' (apenas
     * para os empates em valor).
     * 
     * @param pesoMaximoDaMochila
     *                                      Peso máximo suportado pela mochila
     * @param listaDeObjetosDisponiveis
     *                                      Arranjo de objetos considerados no
     *                                      problema
     * 
     * @return Mochila carregada conforme essa estratégia
     */
    public static Mochila utilizaMaiorValor(double pesoMaximoDaMochila, Objeto[] listaDeObjetosDisponiveis) {
	Stack<Objeto> lista = construirPilha(listaDeObjetosDisponiveis);
	lista.sort(compararPorValor());
	Mochila mochila = new Mochila(pesoMaximoDaMochila);
	while (!lista.empty()) {
	    Objeto item = lista.pop();
	    if (validarPesoMochila(mochila, item)) {
		mochila.setPesoUsado(mochila.getPesoUsado() + item.getPeso());
		mochila.setValorDentroDaMochila(mochila.getValorDentroDaMochila() + item.getValor());
		mochila.setNumObjetosNaMochila(mochila.getNumObjetosNaMochila() + 1);
	    }
	}

	return mochila;
    }

    /**
     * Este método deve implementar um algoritmo guloso que selecione objetos da
     * listaDeObjetosDisponiveis a serem colocados na mochila, de acordo com o
     * critério 'objetos de maior valor/peso primeiro (valor dividido por peso
     * primeiro)'. Caso dois objetos tenham o mesmo valor/peso, o critério de
     * desempate sera 'objetos de maior peso primeiro' (apenas para os empates).
     * 
     * @param pesoMaximoDaMochila
     *                                      Peso máximo suportado pela mochila
     * @param listaDeObjetosDisponiveis
     *                                      Arranjo de objetos considerados no
     *                                      problema
     * 
     * @return Mochila carregada conforme essa estratégia
     */
    public static Mochila utilizaMaiorValorDivididoPorPeso(double pesoMaximoDaMochila,
	    Objeto[] listaDeObjetosDisponiveis) {
	Stack<Objeto> lista = construirPilha(listaDeObjetosDisponiveis);
	lista.sort(compararPorCusto());
	Mochila mochila = new Mochila(pesoMaximoDaMochila);
	while (!lista.empty()) {
	    Objeto item = lista.pop();
	    if (validarPesoMochila(mochila, item)) {
		mochila.setPesoUsado(mochila.getPesoUsado() + item.getPeso());
		mochila.setValorDentroDaMochila(mochila.getValorDentroDaMochila() + item.getValor());
		mochila.setNumObjetosNaMochila(mochila.getNumObjetosNaMochila() + 1);
	    }
	}

	return mochila;
    }

    private static Stack<Objeto> construirPilha(Objeto[] lista) {
	Stack<Objeto> pilha = new Stack<Objeto>();
	for (Objeto item : lista) {
	    pilha.add(item);
	}
	return pilha;
    }
    
    private static boolean validarPesoMochila(Mochila mochila, Objeto item) {
	try {
	    if (item.getPeso() <= 0 || item.getValor() < 0) {
		return false;
	    }
	    return mochila.getPesoUsado() + item.getPeso() <= mochila.getPesoMaximo(); 
	} catch (Exception e) {
	    return false;
	}
    }

    private static Comparator<Objeto> compararPorPeso() {
	Comparator comp = new Comparator<Objeto>() {
	    @Override
	    public int compare(Objeto obj1, Objeto obj2) {
		if (obj1 == null || obj1 == null) {
		    return 0;
		}
		Double peso2 = obj2.getPeso();
		int resultadoPorPeso = peso2.compareTo(obj1.getPeso());
		if (resultadoPorPeso == 0) {
		    Double valor2 = obj2.getValor();
		    return valor2.compareTo(obj1.getValor());
		} else {
		    return resultadoPorPeso;
		}
	    }
	};
	return comp;
    }

    private static Comparator<Objeto> compararPorValor() {
	Comparator comp = new Comparator<Objeto>() {
	    @Override
	    public int compare(Objeto obj1, Objeto obj2) {
		if (obj1 == null || obj1 == null) {
		    return 0;
		}
		Double valor1 = obj1.getValor();
		int resultadoPorValor = valor1.compareTo(obj2.getValor());
		if (resultadoPorValor == 0) {
		    Double peso2 = obj2.getPeso();
		    return peso2.compareTo(obj1.getPeso());
		} else {
		    return resultadoPorValor;
		}
	    }
	};
	return comp;
    }

    private static Comparator<Objeto> compararPorCusto() {
	Comparator comp = new Comparator<Objeto>() {
	    @Override
	    public int compare(Objeto obj1, Objeto obj2) {
		if (obj1 == null || obj1 == null) {
		    return 0;
		}
		Double custo1 = obj1.getValor() / obj1.getPeso();
		Double custo2 = obj2.getValor() / obj2.getPeso();
		int resultadoCusto = custo1.compareTo(custo2);
		if (resultadoCusto == 0) {
		    Double peso1 = obj1.getPeso();
		    return peso1.compareTo(obj2.getPeso());
		} else {
		    return resultadoCusto;
		}
	    }
	};
	return comp;
    }

}
