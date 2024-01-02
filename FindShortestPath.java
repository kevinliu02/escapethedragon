import java.io.IOException;
import java.io.FileNotFoundException;

public class FindShortestPath {
    public static void main(String[] args) {
    	
        try {
            if (args.length <= 0) {
            	throw new Exception("No input file specified");
            }
            
            //create new empty priority queue as instructions say
            DLPriorityQueue<Hexagon> priorityQueue = new DLPriorityQueue<>();
           
           
            //get starting chamber from class Dungeon
            Dungeon dungeon = new Dungeon(args[0]);
            Hexagon curr = dungeon.getStart();
            
            //add starting chamber to queue with 0 priority
            priorityQueue.add(curr, 0);
           
            //current chamber added to queue, so mark it as enqueued.
            curr.markEnqueued();
            
            //while queue is not empty and exit has not been found
            while (priorityQueue.isEmpty() == false && curr.isExit() == false) {
            	
            	
            	//remove smallest priority chamber  from the queue 
                curr = priorityQueue.removeMin();
                //now mark it as dequeued from the queue
                curr.markDequeued();
                //if current chamber is exit, then immediately break/exit the while loop
                if (curr.isExit()) {
                    break;
                }
                
                boolean hasDragon = false;
                
                for (int i = 0; i < 6; i++) {
         
                	//if current chamber is dragon or any neighbouring chambers has dragon, then continue back to while loop and 
                	//try new path
                    if (curr.getNeighbour(i) != null && (curr.isDragon() || curr.getNeighbour(i).isDragon())) {
                        hasDragon = true;
                        continue;   
                    }
               }
                
                
                
                //if current chamber is not dragon and all neighbouring chambers are not dragons then proceed
                if (hasDragon == false) {
                	int D;
                    for (int i = 0; i < 6; i++) {
                    	
                    	//if neighbour is not null and not type wall and not dequeued then proceed
                        if (curr.getNeighbour(i)!= null && !curr.getNeighbour(i).isWall() && !curr.getNeighbour(i).isMarkedDequeued()) {
                            D = curr.getDistanceToStart();
                            D++;
                            
                            
                            
                            int distFromNeighbourToStart = curr.getNeighbour(i).getDistanceToStart();
                            double distFromNeighbourToExit = curr.getNeighbour(i).getDistanceToExit(dungeon);
                            
                            //if the distance between current's neighbour and the start chamber is larger than D then set the distance from neighbour
                            //to start chamber to D
                            if (D < distFromNeighbourToStart) {
                                curr.getNeighbour(i).setDistanceToStart(D);
                                distFromNeighbourToStart = D;
                                
                                //set current as the predecessor of current
                                //so that at the end the program can follow the predecessors to create a path back to start
                                curr.getNeighbour(i).setPredecessor(curr);
                                
                                //if neighbour is enqueued and distance between the neighbour and start equals D instead of its previous value, then update priority of current's neighbour
                                if (curr.getNeighbour(i)!= null && curr.getNeighbour(i).isMarkedEnqueued() && D == distFromNeighbourToStart) 
                                	
                                	//the neighbour's new priority equals the sum of the distance from start chamber to neighbour and distance from the neighbour to the exit
                                    priorityQueue.updatePriority(curr.getNeighbour(i), distFromNeighbourToStart + distFromNeighbourToExit);
                                
                                
                                
                                
                                //otherwise, the neighbour is not added already in the queue then add it with the same priority calculations as in the line above
                                else if (curr.getNeighbour(i).isMarkedEnqueued() == false)
                                
                                    priorityQueue.add(curr.getNeighbour(i), distFromNeighbourToStart + distFromNeighbourToExit);
                                	
                                //curr.getNeighbour(i).markEnqueued();
                            }
                        }
                    }
                }
            }
            
        //empty priority queue is thrown if priority queue is empty
        } catch (EmptyPriorityQueueException e) {
            System.out.println("Priority queue is empty: " + e.getMessage());
            
        //invalid element exception is thrown if data item is not in priority queue
        } catch (InvalidElementException e) {
            System.out.println("Invalid element in priority queue: " + e.getMessage());
                
        //invalid dungeon character exception is thrown if the input file contains an invalid character
        } catch (InvalidDungeonCharacterException e) {
            System.out.println("Invalid character in input file: " + e.getMessage());
            
        } catch(FileNotFoundException e) {
        	System.out.println("Input file not found " + e.getMessage());
        	
        } catch (IOException e) {
        	System.out.println("Error reading input file" + e.getMessage());
        	
        //catch all other exceptions
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
}