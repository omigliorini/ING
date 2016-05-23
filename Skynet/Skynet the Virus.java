


import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    static private ArrayList<Vector<Integer>> links = new ArrayList<Vector<Integer>>();
    //static private int longSol=0;
    static private Vector<String> linkPerformed = new Vector<String>();
    static int N; // the total number of nodes in the level, including the gateways
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        N = in.nextInt(); // the total number of nodes in the level, including the gateways
        int L = in.nextInt(); // the number of links
        int E = in.nextInt(); // the number of exit gateways
        
        for(int i=0;i<N;i++) links.add(i,new Vector<Integer>()); // links initialization 
        
        for (int i = 0; i < L; i++) {
            int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
            int N2 = in.nextInt();
            Vector<Integer> link=(Vector<Integer>)links.get(N1);
            link.add(N2);
            links.set(N1,link);
            
            link=(Vector<Integer>)links.get(N2); // links are bidirectional
            link.add(N1);
            links.set(N2,link);
        }

        Vector<Integer> gateway = new Vector<Integer>(); // vector of Gateways
        for (int i = 0; i < E; i++) {
            gateway.add(in.nextInt());
        }


        // game loop
        while (true) {
            int SI = in.nextInt(); // The index [of]the node on which the Skynet agent is positioned this turn
            //longSol=N;
            String res = shortPath(SI,gateway);
            System.out.println(res);
        }
    }

    static String shortPath(int node, Vector<Integer> gateways)
    {
        
        Vector<String> wePassBy = new Vector<String>();
        Vector<Integer> nearestLink = new Vector<Integer>(Arrays.asList(0,0));
        findNearestGateway(node,gateways,wePassBy,nearestLink,N);
        linkPerformed.add(nearestLink.get(0)+" "+nearestLink.get(1));
        linkPerformed.add(nearestLink.get(1)+" "+nearestLink.get(0));
        return nearestLink.get(0)+" "+nearestLink.get(1);
    }

    // to block agent fastly - we 'll search the nearest gateway from agent
    static void findNearestGateway(int node, Vector<Integer> gateways,Vector<String> wePassBy,Vector<Integer> nearestLink,int longSol)
    {
        if(wePassBy.contains(""+node)) return; // node has been done yet - no cycle
        if(longSol<=wePassBy.size()+1) return; // Path is too long - the short path from agent to gateway
        wePassBy.add(""+node);
        Vector<Integer> linkedNode=(Vector<Integer>)links.get(node); // we'll work on the child

        for(int i=0;i<linkedNode.size();i++)
        {
                    
            if(gateways.contains(linkedNode.get(i))) // child is a gateway - we stop searching
            {
                // the node is a gateway
                if(linkPerformed.contains(node+" "+linkedNode.get(i))) continue; // link has been done in the past
                longSol=wePassBy.size();
                nearestLink.set(0,node);
                nearestLink.set(1,linkedNode.get(i));
            }
            else
                findNearestGateway(linkedNode.get(i),gateways,wePassBy,nearestLink,longSol); // Search deeper in childs
        }
        wePassBy.remove(""+node);
    }
 
}