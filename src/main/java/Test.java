import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
	public static void main(String[] args) {
		// double s = 0;
		// for (int i = 0; i < 26; i++)
		// s += 0.1;
		// System.out.println(s);
		//
		//// System.out.println(new Float(0.0).compareTo((float) -0.0));
		//
		// double d = 29.0 * 0.01;
		// System.out.println(d);
		// System.out.println((int) (d * 100));

		// LocalDate endOfFeb = LocalDate.parse("2014-02-28");
		// System.out.println(endOfFeb);

		// List<LocalTime> localTimes = Stream.iterate(LocalTime.of(0, 5), time
		// -> time.plusMinutes(5)).limit(24*60/5).collect(Collectors.toList());

		// localTimes.forEach(System.out :: println);

		// System.out.println("=============" + LocalTime.of(0,
		// 20).isBefore(LocalTime.of(0, 5)));
		//
		//
		// localTimes.stream().filter(x -> !LocalTime.of(0, 30).isAfter(x))
		// .findFirst().ifPresent(System.out :: println);;

//		LocalDate date = LocalDate.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
//		String text = date.format(formatter);
//		System.out.println(text);
//		LocalDate parsedDate = LocalDate.parse(text, formatter);
//		System.out.println(parsedDate);
		
		System.out.println(LocalTime.parse("10:00", DateTimeFormatter.ofPattern("HH:mm")));
		System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

		// System.out.println(LocalDate.now());

	}
}
