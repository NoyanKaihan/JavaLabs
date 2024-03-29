package collection;

import colorizedConsole.ConsoleColor;
import modules.Color;
import modules.Person;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionManager {
    private HashSet<Person> collection;
    private HashSet<Integer> ids;
    private ZonedDateTime initializationDate;

    public CollectionManager() {
        this.collection = new HashSet<>();
        this.ids = new HashSet<>();
        initializationDate = ZonedDateTime.now();
    }

    public void setCollection(HashSet<Person> collection) {
        this.collection = collection;
    }

    public HashSet<Person> getCollection() {
        return collection;
    }

    public void setIds(HashSet<Integer> ids) {
        this.ids = ids;
    }

    public HashSet<Integer> getIds() {
        return ids;
    }

    public void add(Person person) {
        person.setId(generateNextId());
        collection.add(person);
        System.out.println(person);
    }

    public String collectionInfo() {
        return ConsoleColor.MAGENTA_BOLD + "HashSet of Person" + ConsoleColor.RESET + "(HashSet<Person>) with" + ConsoleColor.MAGENTA_BOLD + " size of #" + ConsoleColor.RESET + collection.size() + ConsoleColor.MAGENTA_BOLD + " initialized in [" + ConsoleColor.RESET + initializationDate + ConsoleColor.MAGENTA_BOLD + "]" + ConsoleColor.RESET;
    }

    private int generateNextId() {
        if (collection.isEmpty())
            return 1;
        else {
            Integer id = getLastElement().getId() + 1;
            if (ids.contains(id)) {
                while (ids.contains(id)) id += 1;
            }
            ids.add(id);
            return id;
        }
    }

    private Person getLastElement() {
        ArrayList<Person> array = new ArrayList<>(collection);
        return array.get(array.size() - 1);
    }

    public boolean updateById(int id, Person person) {
        boolean isUpdated = false;
        Optional<Person> personToUpdate = collection.stream().filter(p -> p.getId() == id).findAny();
        if (personToUpdate.isPresent()) {
            Person p1 = personToUpdate.get();
            p1.setName(person.getName());
            p1.setHairColor(person.getHairColor());
            p1.setLocation(person.getLocation());
            p1.setWeight(person.getWeight());
            p1.setBirthday(person.getBirthday());
            p1.setCreationDate(person.getCreationDate());
            p1.setHeight(person.getHeight());
            p1.setCoordinates(person.getCoordinates());
            isUpdated = true;
        }
        return isUpdated;
    }

    public boolean removeById(int id) {
        return collection.removeIf(p -> p.getId().compareTo(id) == 0);
    }

    public void clear() {
        collection.clear();
    }

    public boolean addIfMin(Person person) {
        boolean isAdded = false;
        Optional<Person> minPerson = collection
                .stream()
                .filter(p -> p.compareTo(person) >= 1)
                .findAny();
        if (minPerson.isPresent()) {
            add(person);
            isAdded = true;
        }
        return isAdded;
    }

    public boolean removeGreater(Person person) {
        return collection.removeIf(p -> p.compareTo(person) > 0);
    }

    public boolean removeLower(Person person) {
        return collection.removeIf(p -> p.compareTo(person) < 0);
    }

    public boolean removeAllByHairColor(Color color) {
        List<Color> colors = collection
                .stream()
                .map(Person::getHairColor)
                .filter(Objects::nonNull)
                .toList();
        if (colors.contains(color)) {
            return collection.removeIf(p -> p.getHairColor() != null && p.getHairColor().name().equals(color.name()));
        }
        return false;
    }

    public List<Person> filterByBirthday(Date birthday) {
        System.out.println(birthday);
        return collection
                .stream()
                .filter(p -> p.getBirthday() != null && p.getBirthday().equals(birthday))
                .collect(Collectors.toList());
    }

    public Set<Date> descendingBirthday() {
        Set<Person> personWithDates = collection
                .stream()
                .filter(p -> p.getBirthday() != null)
                .sorted(Comparator.comparing(Person::getBirthday).reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        Set<Date> dates = personWithDates
                .stream()
                .map(Person::getBirthday)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return dates;
    }
}
