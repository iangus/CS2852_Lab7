/*
 * CS2852 - 041
 * Spring 2016
 * Lab 7
 * Name: Ian Guswiler
 * Created: 4/26/2016
 */

/**
 * Morse code tree. Root should always be an empty node. Left children are dots and right children are dashes.
 *
 * @author Ian Guswiler
 * @version 5/2/16
 * @param <E> Generic type object to be stored in the MorseTree
 */
public class MorseTree<E> {
    private Node<E> root;

    private static class Node<E>{
        private E value;
        private Node<E> rKid;
        private Node<E> lKid;

        private Node(E value, Node<E> rKid, Node<E> lKid){
            this.value = value;
            this.rKid = rKid;
            this.lKid = lKid;
        }

        private Node(E value){
            this(value,null,null);
        }
    }

    /**
     * Constructor that creates a new MorseTree with an empty root
     */
    public MorseTree(){
        root = new Node<E>(null);
    }

    /**
     * adds a symbol object to the tree in a position based off of the provided morse pattern
     * @param symbol generic object to be stored in the specified position
     * @param morsePattern specifies the position in the tree
     */
    public void add(E symbol, String morsePattern){
        Node<E> subroot = root;
        for(int i = 0; i<morsePattern.length(); i++){
            char character = morsePattern.charAt(i);
            if(character == '-'){
                if(subroot.rKid == null){
                    subroot.rKid = new Node(null);
                }
                subroot = subroot.rKid;
            } else if(character == '.'){
                if(subroot.lKid == null){
                    subroot.lKid = new Node(null);
                }
                subroot = subroot.lKid;
            }
        }
        subroot.value = symbol;
    }

    /**
     * Decodes the provided morse encoded string
     * @param morsePattern morse encoded string
     * @return decoded character. Returns null if the pattern doesn't exist. Throws an exception if characters other
     * than '.' or '-' are found
     */
    public E decode(String morsePattern){
        Node<E> subroot = root;
        boolean found = false;
        try {
            for(int i = 0; i<morsePattern.length(); i++){
                char character = morsePattern.charAt(i);
                if(character == '-'){
                    subroot = subroot.rKid;
                } else if(character == '.'){
                    subroot = subroot.lKid;
                } else{
                    throw new IllegalArgumentException("The morse pattern string can only contain the characters '.' and '-';" +
                            " the input String contained the character '" + character + "', so it was skipped.");
                }
            }

            found = true;
        } catch (NullPointerException e) {
            //catching to stop the loop if the code does not exist
        }
        return found ? subroot.value : null;
    }
}
