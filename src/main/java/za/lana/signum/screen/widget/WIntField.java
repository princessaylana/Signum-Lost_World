package za.lana.signum.screen.widget;

import io.github.cottonmc.cotton.gui.widget.WTextField;

import java.util.Scanner;
import java.util.function.Predicate;

public class WIntField extends WTextField {

    @Override
    public WTextField setTextPredicate(Predicate<String> predicate_1) {
        forceStringToInt();
        return this;
    }
    public void forceStringToInt() {
        boolean flag;
        String n;
        do
        {
            System.out.println("Enter Integer Value only");
            //Scanner sc = new Scanner(System.in);
            Scanner sc = new Scanner(System.in);
            n = sc.next();
            try
            {
                Integer.parseInt(n);
                flag=false;
            }
            catch(NumberFormatException e)
            {
                System.out.println("Enter only integer value");
                flag=true;
            }
        }while(flag);
        System.out.println("Integer value is "+n);
    }
}
