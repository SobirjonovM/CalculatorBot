import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import jdk.dynalink.linker.LinkerServices;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.*;

public class CalculatorUtil {
    public static InlineKeyboardButton button(String text, String callBack) {
        InlineKeyboardButton button = new InlineKeyboardButton(text);
        button.setCallbackData(callBack);
        return button;
    }
    public static InlineKeyboardMarkup getmenu(){
        InlineKeyboardButton button1 = CalculatorUtil.button("1", "one");
        InlineKeyboardButton button2 = CalculatorUtil.button("2", "two");
        InlineKeyboardButton button3 = CalculatorUtil.button("3", "three");

        List<InlineKeyboardButton> row1 = new LinkedList<>();
        row1.add(button1);
        row1.add(button2);
        row1.add(button3);

        InlineKeyboardButton button4 = CalculatorUtil.button("4", "four");
        InlineKeyboardButton button5 = CalculatorUtil.button("5", "five");
        InlineKeyboardButton button6 = CalculatorUtil.button("6", "six");

        List<InlineKeyboardButton> row2 = new LinkedList<>();
        row2.add(button4);
        row2.add(button5);
        row2.add(button6);

        InlineKeyboardButton button7 = CalculatorUtil.button("7", "seven");
        InlineKeyboardButton button8 = CalculatorUtil.button("8", "eight");
        InlineKeyboardButton button9 = CalculatorUtil.button("9", "nine");

        List<InlineKeyboardButton> row3 = new LinkedList<>();
        row3.add(button7);
        row3.add(button8);
        row3.add(button9);

        InlineKeyboardButton zeroButton = CalculatorUtil.button("0", "zero");
        List<InlineKeyboardButton> zeroRow = new LinkedList<>();
        zeroRow.add(zeroButton);

        InlineKeyboardButton plusButton = CalculatorUtil.button("+", "plus");
        InlineKeyboardButton minusButton = CalculatorUtil.button("-", "minus");
        InlineKeyboardButton starButton = CalculatorUtil.button("*", "star");
        InlineKeyboardButton divisionButton = CalculatorUtil.button("/", "division");

        List<InlineKeyboardButton> row4 = new LinkedList<>();
        row4.add(plusButton);
        row4.add(minusButton);
        row4.add(starButton);
        row4.add(divisionButton);

        InlineKeyboardButton equalButton = CalculatorUtil.button("=", "equal");
        List<InlineKeyboardButton> row5 = new LinkedList<>();
        row5.add(equalButton);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new LinkedList<>();
        rowList.add(row1);
        rowList.add(row2);
        rowList.add(row3);
        rowList.add(zeroRow);
        rowList.add(row4);
        rowList.add(row5);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
