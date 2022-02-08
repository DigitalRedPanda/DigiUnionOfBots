package TwitchBot;

import java.util.ArrayList;

public class Scan {
    static boolean FindSI(String Context){
        String[] SplittedContext = Context.split(" ");
        char[] CharactersWithinContext;
        for(String NavigateContext: SplittedContext){
            if(NavigateContext.toLowerCase().contains("si")){
                CharactersWithinContext = NavigateContext.toLowerCase().toCharArray();
                for(char FindUnrelatedCharacter:CharactersWithinContext){
                    if(FindUnrelatedCharacter !='s' && FindUnrelatedCharacter !='i')
                        return false;
                }
                return true;
            }}

        return false;
    }/*static String PullWinner(ArrayList<String> Nominees){
       String[] Contenders = new String[Nominees.size()];
       for(int TransferConterdersByIndex = 0; TransferConterdersByIndex<Contenders.length-1; TransferConterdersByIndex++){
           Contenders[TransferConterdersByIndex] = Nominees.get(TransferConterdersByIndex);
       }
       int Index = (int) Math.round(Math.random() * Nominees.size());
        return Contenders[Index];
    }*/
}