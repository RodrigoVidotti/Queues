/****************************************************************
 Nome: Rodrigo Vidotti de Souza
 NUSP: 10687896

 Ao preencher esse cabeçalho com o meu nome e o meu número USP,
 declaro que todas as partes originais desse exercício programa (EP)
 foram desenvolvidas e implementadas por mim e que portanto não
 constituem desonestidade acadêmica ou plágio.
 Declaro também que sou responsável por todas as cópias desse
 programa e que não distribui ou facilitei a sua distribuição.
 Estou ciente que os casos de plágio e desonestidade acadêmica
 serão tratados segundo os critérios divulgados na página da
 disciplina.
 Entendo que EPs sem assinatura devem receber nota zero e, ainda
 assim, poderão ser punidos por desonestidade acadêmica.

 Abaixo descreva qualquer ajuda que você recebeu para fazer este
 EP.  Inclua qualquer ajuda recebida por pessoas (inclusive
 monitoras e colegas). Com exceção de material de MAC0323, caso
 você tenha utilizado alguma informação, trecho de código,...
 indique esse fato abaixo para que o seu programa não seja
 considerado plágio ou irregular.

 Exemplo:

 A monitora me explicou que eu devia utilizar a função xyz().

 O meu método xyz() foi baseada na descrição encontrada na
 página https://www.ime.usp.br/~pf/algoritmos/aulas/enumeracao.html.

 Descrição de ajuda ou indicação de fonte:

 Usei como inspiracao o que foi apresentado na descricao do EP4 no PACA no link:
 https://algs4.cs.princeton.edu/13stacks/


 Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:
 ****************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;    // comeco do deque
    private Node<Item> last;     // final do deque
    private int n;               // numero de elementos no deque

    // Constroi um deque
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;    // lista duplamente ligada
    }

    // Inicializa um deque vazio
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // confere se o deque esta vazio
    public boolean isEmpty() {
        return first == null;
    }

    // retorna o numero de itens no deque
    public int size() {
        return n;
    }

    // adiciona um item no comeco
    public void addFirst(Item item) {
        // corner case, chamada com null
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if (oldFirst != null) {
            oldFirst.previous = first;
        }

        // arruma parâmetro caso o deque esteja vazio
        if (last == null) {
            last = first;
        }

        n++;
    }

    // adiciona um item no final
    public void addLast(Item item) {
        // corner case, chamada com null
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if (oldLast != null) {
            oldLast.next = last;
        }

        // arruma parâmetro caso o deque esteja vazio
        if (isEmpty()) {
            first = last;
        }

        n++;
    }

    // remove um item do comeco
    public Item removeFirst() {
        // corner case, remocao com deque vazio
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = first.item;
        first = first.next;

        // evitar loitering
        if (isEmpty()) {
            last = null;
        }
        // evita tratar null como parametro
        else {
            first.previous = null;
        }

        n--;
        return item;
    }

    // remove um item do final
    public Item removeLast() {
        // corner case, remocao com deque vazio
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        last = last.previous;

        // evitar loitering
        if (last == null) {
            first = null;
        }
        // evita tratar null como parametro
        else {
            last.next = null;
        }

        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        Deque<Integer> deque = new Deque<Integer>();

        // teste de deque inserindo pelo comeco
        for (int i = 0; i < n; i++) {
            deque.addFirst(i);
        }
        // testando iteracao
        for (int a : deque) {
            for (int b : deque) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }
        StdOut.println();

        int nDeque = deque.size();
        StdOut.printf("quantidade de itens adicionados no deque: %d\nquantidade de itens no deque: %d\n\n", n, nDeque);
        // testando remocao
        for (int i = 0; i < n; i++) {
            int x = deque.removeFirst();
            StdOut.printf("o item '%d' foi removido\n", x);
        }
        StdOut.println();

        // teste de deque inserindo pelo final
        for (int i = 0; i < n; i++) {
            deque.addLast(i);
        }
        // testando iteracao
        for (int a : deque) {
            for (int b : deque) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }
        StdOut.println();

        // testando remocao
        for (int i = 0; i < n; i++) {
            int x = deque.removeLast();
            StdOut.printf("o item '%d' foi removido\n", x);
        }
        StdOut.println();

        // teste de corner case
        StdOut.println("Testando corner case, remover item sem item no deque\n");
        deque.removeFirst();
    }
}
