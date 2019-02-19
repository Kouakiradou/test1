package uk.ac.ucl.bag;

public class FileOperator
{
    public <T extends Comparable> void writeBag(Bag<T> bag)
    {
        FileOutput fout = new FileOutput("Data.txt");
        Boolean first = true;
        for (T value : bag)
        {
            if (first)
            {
                fout.writeString(value.getClass().getSimpleName() + "\n");
                first = false;
            }
            fout.writeString("" + value);
            fout.writeString(":");
            fout.writeString("" + bag.countOf(value));
            fout.writeEndOfLine();
        }
        fout.close();
    }

    public <T extends Comparable> Bag<T> loadBag() throws BagException
    {
        FileInput fin = new FileInput("Data.txt");
      //  Bag<String> stringResult = BagFactory.getInstance().getBag();
      //  Bag<Integer> integerResult = BagFactory.getInstance().getBag();
     //   Bag<Long> longResult = BagFactory.getInstance().getBag();
      //  Bag<Float> floatResult = BagFactory.getInstance().getBag();
        Bag<T> result = BagFactory.getInstance().getBag();
        String type = fin.nextLine();
        while(fin.hasNextLine())
        {
            String currentLine = fin.nextLine();
            int signpos1 = currentLine.indexOf(':');
            String value = currentLine.substring(0, signpos1);
            int count = Integer.valueOf(currentLine.substring(signpos1 + 1));
            switch (type)
            {
                case "String":
                    T stringValue = (T) value.toString();
                    result.addWithOccurrences(stringValue, count);
                    break;
                case "Integer":
                    T integerValue = (T) Integer.valueOf(value);
                    result.addWithOccurrences(integerValue, count);
                    break;
                case "Long":
                    T longValue = (T) Long.valueOf(value);
                    result.addWithOccurrences(longValue, count);
                    break;
                case "Float":
                    T floatValue = (T) Float.valueOf(value);
                    result.addWithOccurrences(floatValue, count);
                    break;
            }
        }
        return result;
    }
}
