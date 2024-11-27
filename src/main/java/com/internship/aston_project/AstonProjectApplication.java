package com.internship.aston_project;

import com.internship.aston_project.factory.BusFactory;
import com.internship.aston_project.factory.ObjectFactory;
import com.internship.aston_project.factory.StudentFactory;
import com.internship.aston_project.factory.UserFactory;
import com.internship.aston_project.sort.QuickSort;
import com.internship.aston_project.sort.SortStrategy;
import com.internship.aston_project.utils.BinarySearch;
import com.internship.aston_project.utils.FileUtils;
import com.internship.aston_project.utils.PropertiesLoader;
import com.internship.aston_project.utils.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.internship.aston_project.utils.FileUtils.parseLineByType;

public class AstonProjectApplication {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		System.out.println("Добро пожаловать в программу управления данными!");
		while (running) {
			System.out.println("""
				Выберите тип данных:
				1. Автобусы
				2. Студенты
				3. Пользователи
				4. Выйти""");

			String typeChoice = scanner.nextLine();
			switch (typeChoice) {
				case "1" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new BusFactory(), "1");
				case "2" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new StudentFactory(), "2");
				case "3" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new UserFactory(),"3");
				case "4" -> {
					System.out.println("Выход из программы. До свидания!");
					running = false;
				}
				default -> System.out.println("Неверный выбор. Попробуйте снова.");
			}
		}
		scanner.close();
	}

	private static <T extends Comparable<T>> void manageData(List<T> data, SortStrategy<T> sortStrategy, Scanner scanner, ObjectFactory<T> factory, String type) {
		BinarySearch<T> binarySearch = new BinarySearch<>();
		boolean managing = true;

		while (managing) {
			System.out.printf("Управление данными типа %s:%n", factory.getClass().getSimpleName());
			System.out.println("""
				1. Заполнить данные
				2. Отсортировать данные
				3. Найти элемент (Бинарный поиск)
				4. Сохранить данные в файл
				5. Назад""");

			String choice = scanner.nextLine();
			switch (choice) {
				case "1" -> {
                    try {
                        data = fillData(scanner, factory, type);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
				case "2" -> {
					if (data.isEmpty()) {
						System.out.println("Данные отсутствуют. Сначала заполните массив.");
					} else {
						sortStrategy.sort(data);
						System.out.println("Данные успешно отсортированы.");
						System.out.println("Отсортированный массив: " + data);
					}
				}
				case "3" -> {
					if (data.isEmpty()) {
						System.out.println("Данные отсутствуют. Сначала заполните массив.");
					} else {
						System.out.println("Введите код для поиска: ");
						String searchKey = scanner.nextLine();
						T target = factory.createForSearch(searchKey);
						if (target != null) {
							List<T> foundItems = binarySearch.searchAll(data, target);
							if (!foundItems.isEmpty()) {
								System.out.println("Найденные элементы:");
								for (T item : foundItems) {
									System.out.println(item);
								}
							} else {
								System.out.println("Элементы не найдены.");
							}
						} else {
							System.out.println("Некорректный ввод для поиска.");
						}
					}
				}
				case "4" -> {
					if (data.isEmpty()) {
						System.out.println("Данные отсутствуют. Сначала заполните массив."); // olga_pronina
					} else {
						System.out.println(PropertiesLoader.getAddressBasedOnType(type) + " 1"); //TODO
						saveDataToFile(type, PropertiesLoader.getAddressBasedOnType(type), data);
						System.out.println("Данные сохранены в файл");
					}
				}
				case "5" -> managing = false;
				default -> System.out.println("Неверный выбор. Попробуйте снова.");
			}
		}
	}

	private static <T extends Comparable<T>> List<T> fillData(Scanner scanner, ObjectFactory<T> factory, String type) throws IOException {
		List<T> data = new ArrayList<>();
		System.out.println("""
			Выберите способ заполнения:
			1. Вручную
			2. Из файла
			3. Рандомно""");

		String choice = scanner.nextLine();
		switch (choice) {
			case "1" -> {
				System.out.println("Введите количество элементов:");
				String countInput = scanner.nextLine();
				if (Validator.isValidInteger(countInput)) {
					int count = Integer.parseInt(countInput);
					for (int i = 0; i < count; i++) {
						System.out.printf("Введите данные для элемента %d:%n", i + 1);
						T item = factory.create(scanner);
						if (item != null) {
							data.add(item);
						} else {
							System.out.println("Ошибка ввода. Повторите.");
							i--;
						}
					}
				} else {
					System.out.println("Ошибка: введите целое число.");
				}
			}
			case "2" -> {
				List<String> listFromFile = FileUtils.readFile(PropertiesLoader.getAddressBasedOnType(type));
				 // [Number: 101, Model: Mercedes Sprinter, Mileage: 120000.5, Number: 202, Model: Volvo B8R, Mileage: 90000.0, Number: 303, Model: MAN Lion's Coach, Mileage: 75000.3]
				for (String line : listFromFile) {
					String formattedInput = parseLineByType(line, type);
					System.out.println(formattedInput); //TODO
					System.out.println(factory.getClass());
					try (Scanner lineScanner = new Scanner(formattedInput)) {
						T object = factory.create(lineScanner);
						if (object != null) {
							data.add(object);
						}
					}
				}
					System.out.println("Данные успешно загружены из файла.");
				System.out.println(data);//TODO
					return data;
			}

			case "3" -> {
				System.out.println("Введите количество элементов:");
				String countInput = scanner.nextLine();
				if (Validator.isValidInteger(countInput)) {
					int count = Integer.parseInt(countInput);
					for (int i = 0; i < count; i++) {
						data.add(factory.generateRandom());
					}
					System.out.println("Данные успешно сгенерированы.");
				} else {
					System.out.println("Ошибка: введите целое число.");
				}
			}
			default -> System.out.println("Неверный выбор. Попробуйте снова.");
		}
		return data;
	}


	private static <T extends Comparable<T>> void saveDataToFile(String type, String filePath, List<T> data) {
		List<List<String>> lines = new ArrayList<>(); // olga_pronina

		System.out.println(data + "data"); //TODO
		for (T item : data) {
			lines.add(List.of(item.toString()));
		}
		System.out.println(lines + " saveDataToFile");
		try {
			FileUtils.prepareAndWrite(type, filePath, lines);
			System.out.println("Данные успешно сохранены в файл.");
		} catch (IOException e) {
			System.out.println("Ошибка записи в файл: " + e.getMessage());
		}
	}
}