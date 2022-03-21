import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> lastNames = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    lastNames.get(new Random().nextInt(lastNames.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        /* 1. Find the number of minors (i.e. people under 18) */
        long count = persons.stream()
                .filter(x -> x.getAge() < 18)
                .count();
        System.out.printf("1. Number of minors (i.e. people under 18): %s", count);
        System.out.println();

        /* 2. Get a list of lastNames of conscripts (i.e. men from 18 to 27 years old) */
        List<String> conscripts = persons.stream()
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27 && x.getSex() == Sex.MAN)
                .limit(100) // testing a limit for the output of names in the list
                .map(Person::getLastName).collect(Collectors.toList());
        System.out.printf("2. Last names of conscripts (men from 18 to 27 years old): %s", conscripts);
        System.out.println();

        /*
         * 3. Get a list sorted by last name of potentially able people with higher education in the sample
         * (i.e. people with higher education from 18 to 60 years old for women and up to 65 years old for men)
         */
        List<Person> workers = persons.stream()
                .filter(x -> x.getAge() >= 18 && x.getAge() <= (x.getSex() != Sex.MAN ? 60 : 65) && x.getEducation() == Education.HIGHER)
                .limit(100) // for limiting the output
                .sorted(Comparator.comparing(Person::getLastName))
                .collect(Collectors.toList());
        System.out.printf("3. People with higher education: %s", workers);
    }
}
