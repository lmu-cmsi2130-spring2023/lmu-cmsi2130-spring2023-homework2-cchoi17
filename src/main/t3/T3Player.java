package main.t3;

import java.util.*;

/**
 * Artificial Intelligence responsible for playing the game of T3!
 * Implements the alpha-beta-pruning mini-max search algorithm
 */
public class T3Player {
    
    /**
     * Workhorse of an AI T3Player's choice mechanics that, given a game state,
     * makes the optimal choice from that state as defined by the mechanics of the
     * game of Tic-Tac-Total. Note: In the event that multiple moves have
     * equivalently maximal minimax scores, ties are broken by move col, then row,
     * then move number in ascending order (see spec and unit tests for more info).
     * The agent will also always take an immediately winning move over a delayed
     * one (e.g., 2 moves in the future).
     * 
     * @param state
     *            The state from which the T3Player is making a move decision.
     * @return The T3Player's optimal action.
     */
    public T3Action choose (T3State state) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        int highestUtility = 0;
        T3Action actionToTake = new T3Action(0, 0, highestUtility);
        for(Map.Entry<T3Action, T3State> child : state.getTransitions().entrySet()){
            if(child.getValue().isWin()){
                return child.getKey();
            }
            else{
                var thisUtility = alphabeta(child.getValue(), alpha, beta, true);
                if(thisUtility > highestUtility){
                    highestUtility = thisUtility;
                    alpha = highestUtility;
                    actionToTake = child.getKey();
                }
            }
        }
        return actionToTake; 
        // if agent can win in one turn, should choose that action.
        
    }
    /**
     * This method, given the T3Player's state, values of alpha and beta at that state, and 
     * whether the Player is playing Odds or Evens, returns the utility score. It will also
     * prune transitions where the beta score is greater than or equal to the alpha
     * score, meaning the T3Player will not traverse down a path where it will play not
     * optimally.
     * 
     * @param state, alpha, beta, turn
     *            The state from which the T3Player is making a move decision.
     *            The values of alpha and beta at that state.
     *            Whether the T3Player's turn is Odds(true) or Evens(false).
     * @return The T3Player's utility score.
     */
    public int alphabeta(T3State state, int alpha, int beta, boolean turn){ // returns utility and prunes
        int utility = 0;
        if(state.getMoves() == null){
            return utility;
        }
        if(turn){
            utility = Integer.MIN_VALUE;
            for(Map.Entry<T3Action, T3State> child : state.getTransitions().entrySet()){
                utility = Math.max(utility, alphabeta(child.getValue(), alpha, beta, false));
                alpha = Math.max(alpha, utility);
                if(beta >= alpha){
                    break;
                }
            }
            return utility;   
        }
        else{
            utility = Integer.MAX_VALUE;
            for(Map.Entry<T3Action, T3State> child : state.getTransitions().entrySet()){
                utility = Math.min(utility, alphabeta(child.getValue(), alpha, beta, true));
                beta = Math.min(beta, utility);
                if(beta <= alpha){
                    break;
                }
            }
            return utility;
        }
    } 
}