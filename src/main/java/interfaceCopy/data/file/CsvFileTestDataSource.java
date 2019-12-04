package interfaceCopy.data.file;

import interfaceCopy.data.IByteDataSource;
import interfaceCopy.data.ITestDataSource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CsvFileTestDataSource implements ITestDataSource {
    private Optional<IByteDataSource> byteDataSource;
    private String charset;

    public CsvFileTestDataSource(IByteDataSource byteDataSource, String charset) {
        this.byteDataSource = Optional.of(byteDataSource);
        this.charset = charset;
    }

    @Override
    public Iterator<Object[]> getData() throws Throwable {
        byte[] contentCsvFile = byteDataSource.get().getData();
        String content = new String(contentCsvFile, charset);
        System.out.println("content:" + content);
        CSVParser csvParser = CSVParser.parse(content, CSVFormat.EXCEL);
        List<Object[]> collect = csvParser.getRecords().stream().map(
                input -> CollectionUtils.collect(input, source -> source).toArray()
        ).collect(Collectors.toList());
        return collect.iterator();
    }
}
