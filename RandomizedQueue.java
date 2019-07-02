/****************************************************************
 Nome: Rodrigo Vidotti de Souza
 NUSP: 1068796

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



 Se for o caso, descreva a seguir 'bugs' e limitações do seu programa:
 ****************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Node<Item> first;    // comeco do queue
    private int n;               // numero de elementos no queue

    // Constroi um queue
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    // constroi um queue
    public RandomizedQueue() {
        first = null;
        n = 0;
    }

    // checa se o queue esta vazio
    public boolean isEmpty() {
        return first == null;
    }

    // returna o numero de itens do queue
    public int size() {
        return n;
    }

    // adiciona um item novo
    public void enqueue(Item item) {
        // corner case, chamada com null
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        n++;
    }

    public Node<Item> randomNode() {
        int x = StdRandom.uniform(n);
        Node<Item> node = first;

        for (int i = 0; i < x; i++) {
            node = node.next;
        }

        return node;
    }

    // remove and return a random item
    public Item dequeue() {
        // corner case, remocao com deque vazio
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node<Item> oldFirst = randomNode(); // item que sera removido

        // coloca o item aleatório no comeco

        // StdOut.printf("\nfirst.item: %d\noldFirst.item: %d\n", first.item, oldFirst.item); // debug

        Item aux = first.item;
        first.item = oldFirst.item;
        oldFirst.item = aux;

        //  StdOut.printf("\nfirst.item: %d\noldFirst.item: %d\naux.item: %d\n", first.item, oldFirst.item, aux); // debug

        // agora retorna e remove item aleatório que foi para o comeco
        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        // corner case, remocao com deque vazio
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> node = randomNode();
        return node.item;
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
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        // teste de queue inserindo e duas iterações para provar que são aleatórias e independentes entre si
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }

        int nqueue = queue.size();
        StdOut.printf("quantidade de itens adicionados no queue: %d\nquantidade de itens no queue: %d\n\n", n, nqueue);


        for (int i = 0; i < n; i++) {
            int x = queue.dequeue();
            int tamanho = queue.size();
            StdOut.printf("o item '%d' foi removido\ntamanho do queue: %d\n", x, tamanho);
        }

        // teste de corner case
        StdOut.println("\nTestando corner case, remover item sem item no queue\n");
        queue.dequeue();
    }
}
