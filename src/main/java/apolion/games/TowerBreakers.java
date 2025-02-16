package apolion.games;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.IntStream;

class Pair{
    int m,i;

    public Pair(int m, int i) {
        this.m = m;
        this.i = i;
    }

    @Override
    public boolean equals(Object obj) {
        Pair p = (Pair)obj;
        return p.i==i&&p.m==m;
    }
}

class Move{
    public Move(){}
    int x;
    int y;
    int reduceAmount;
    boolean isAlreadyLinked = false;
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "x"+x+",y:"+y+",redA:"+reduceAmount;
    }
}
class Result {

    public static Map<Integer, LinkedList<LinkedHashSet<Move>>> cacheMoves = new HashMap<>();

    /*
     * Complete the 'towerBreakers' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER m
     */
    public static int towerBreakers(int n, int m) {
        int[] towers = new int[n];
        for(int i=0;i<n;i++){
            towers[i]=m;
        }
        boolean isPlayerOne = true;
        do{
            LinkedList<LinkedList<LinkedHashSet<Move>>> towerSolutions = new LinkedList<>();
            for(int i=0;i<n;i++){
                LinkedList<LinkedHashSet<Move>> solutions = getSolutions(towers[i]);
                towerSolutions.add(solutions);
            }
            boolean isDone = true;
            for(int i=0;i<towerSolutions.size();i++){
                if(towerSolutions.get(i).size()>0){
                    isDone = false;
                    break;
                }
            }
            if(isDone) break;

            int min = Integer.MAX_VALUE;
            Move minMove = null;
            int selectedTower = -1;
            for(int i=0;i<towerSolutions.size();i++){
                if(towerSolutions.get(i).size()==0){
                    continue;
                }
                for(LinkedHashSet<Move> moves : towerSolutions.get(i)){
                    if(moves.size()<min&&moves.size()%2==1){
                        min = moves.size();
                        minMove = moves.getLast();
                        selectedTower = i;
                    }
                }
            }
            if(minMove!=null){
                towers[selectedTower]= minMove.y;
                isPlayerOne=!isPlayerOne;
            } else {
                return isPlayerOne?2:1;
            }
        }while(true);
        return isPlayerOne?2:1;

    }

    public static LinkedList<LinkedHashSet<Move>> getSolutions(int m){
        LinkedList<LinkedHashSet<Move>> result = new LinkedList<>();
        if(cacheMoves.containsKey(m))
            return cacheMoves.get(m);
        result = getAllPossibleMoves(m,1, new LinkedHashSet<>(),result);
        cacheMoves.put(m, result);
        return result;
    }

    public static Map<Pair,LinkedList<LinkedHashSet<Move>>> cache = new HashMap();
    public static LinkedList<LinkedHashSet<Move>> getAllPossibleMoves(int m,int i, LinkedHashSet<Move> currMoves, LinkedList<LinkedHashSet<Move>> possibleMoves){
        while(true){

            if(cache.containsKey(new Pair(m,i))){
                System.out.println("cached");
                return cache.get(new Pair(m,i));
            }
            if(m==1||i>=m||i<=0)
            {
                return possibleMoves;
            }
            int x = m;
            int reduceAmount = m -i;
            int y = m - reduceAmount;
            if(x%y==0){
                Move move = new Move();
                move.x = x;
                move.y = y;
                move.isAlreadyLinked = false;
                move.reduceAmount = reduceAmount;

                Move prevMove = null;
                if(currMoves.size()>0){
                    prevMove = currMoves.getFirst();
                }
                if(prevMove == null||prevMove.isAlreadyLinked){
                    LinkedHashSet<Move> moves = new LinkedHashSet<>(currMoves);
                    moves.add(move);
                    possibleMoves.add(moves);
                    addAllUniqueElements(possibleMoves,getAllPossibleMoves(y,1,moves,possibleMoves));
                    cache.put(new Pair(m,i),possibleMoves);
                } else {
                    currMoves.add(move);
                    prevMove.isAlreadyLinked=true;
                    addAllUniqueElements(possibleMoves,getAllPossibleMoves(y,1,currMoves,possibleMoves));
                    cache.put(new Pair(m,i),possibleMoves);
                }

            }
            i++;
        }
    }

    public static void addAllUniqueElements(LinkedList<LinkedHashSet<Move>> targetList, LinkedList<LinkedHashSet<Move>> sourceList) {
        for (LinkedHashSet<Move> element : sourceList) {
            if (!targetList.contains(element)) {
                targetList.add(element);
            }
        }
    }
}


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        PrintStream out = System.out;
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                int result = Result.towerBreakers(n, m);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
