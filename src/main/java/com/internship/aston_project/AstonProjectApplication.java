package com.internship.aston_project;

//import com.internship.aston_project.factory.BusFactory;
//import com.internship.aston_project.factory.ObjectFactory;
//import com.internship.aston_project.factory.StudentFactory;
//import com.internship.aston_project.factory.UserFactory;
import com.internship.aston_project.sort.QuickSort;
import com.internship.aston_project.sort.SortStrategy;
import com.internship.aston_project.strategy.StrategyFiller;
import com.internship.aston_project.utils.BinarySearch;

import com.internship.aston_project.strategy.BusArrayFiller;
import com.internship.aston_project.strategy.StudentArrayFiller;
import com.internship.aston_project.strategy.UserArrayFiller;
import com.internship.aston_project.utils.FileValidation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
public class AstonProjectApplication {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		System.out.println("Добро пожаловать в программу управления данными!");
		while (running) {
			System.out.println("\nВыберите тип данных:");
			System.out.println("1. Автобусы");
			System.out.println("2. Студенты");
			System.out.println("3. Пользователи");
			System.out.println("4. Выйти");

			String typeChoice = scanner.nextLine();
			switch (typeChoice) {
				case "1" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new BusArrayFiller());
				case "2" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new StudentArrayFiller());
				case "3" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new UserArrayFiller());
				case "4" -> {
					System.out.println("Выход из программы. До свидания!");
					running = false;
				}
				default -> System.out.println("Неверный выбор. Попробуйте снова.");
			}
		}
		scanner.close();
	}

	private static <T extends Comparable<T>> void manageData(List<T> data, SortStrategy<T> sortStrategy, Scanner scanner, StrategyFiller<T> filler) {
		BinarySearch<T> binarySearch = new BinarySearch<>();
		boolean managing = true;

		//TODO: сделала вывод типа в виде классов Bus, Student или User
		String type = null;
		switch(filler.getClass().getSimpleName()){
			case "BusArrayFiller" -> type = "Bus";
			case "StudentArrayFiller" -> type = "Student";
			case "UserArrayFiller" -> type = "User";
		}

		while (managing) {
			System.out.printf("\nУправление данными типа %s:%n", type /*Было: filler.getClass().getSimpleName()*/ );
			System.out.println("1. Заполнить данные");
			System.out.println("2. Отсортировать данные");
			System.out.println("3. Найти элемент (Бинарный поиск)");
			System.out.println("4. Сохранить данные в файл");
			System.out.println("5. Назад");

			String choice = scanner.nextLine();
			switch (choice) {
				case "1" -> data = fillData(scanner, filler);
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
						System.out.println("Данные отсутствуют. Сначала заполните массив.");
					} else {
						System.out.println("Введите путь и имя файла для сохранения данных: ");
						String filePath = scanner.nextLine();
						saveDataToFile(filePath, data);
					}
				}
				case "5" -> managing = false;
				default -> System.out.println("Неверный выбор. Попробуйте снова.");
			}
		}
	}

	private static <T extends Comparable<T>> List<T> fillData(Scanner scanner, StrategyFiller<T> filler) {
		List<T> data = new ArrayList<>();
		System.out.println("Выберите способ заполнения :");
		System.out.println("1. Вручную");
		System.out.println("2. Из файла");
		System.out.println("3. Рандомно");

		String choice = scanner.nextLine();
		switch (choice) {
			case "1" -> {
				System.out.println("Введите количество элементов:");
				String countInput = scanner.nextLine();
				if (FileValidation.validateInteger(countInput)) {
					int count = Integer.parseInt(countInput);
					for (int i = 0; i < count; i++) {
						System.out.printf("Введите данные для элемента %d:%n", i + 1);
						T item = filler.fillManually(scanner, true);
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
				System.out.println("Введите путь к файлу:");
				String filePath = scanner.nextLine();
				try {
					List<String> lines = Files.readAllLines(Path.of(filePath));
					for (String line : lines) {
						T item = filler.fillFromFile(line);
						if (item != null) {
							data.add(item);
						} else {
							System.out.println("Ошибка валидации строки: " + line);
						}
					}
					System.out.println("Данные успешно загружены из файла.");
				} catch (IOException e) {
					System.out.println("Ошибка чтения файла: " + e.getMessage());
				}
			}
			case "3" -> {
				System.out.println("Введите количество элементов:");
				String countInput = scanner.nextLine();
				if (FileValidation.validateInteger(countInput)) {
					int count = Integer.parseInt(countInput);
					for (int i = 0; i < count; i++) {
						data.add(filler.fillFromRandom());
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

	private static <T extends Comparable<T>> void saveDataToFile(String filePath, List<T> data) {
		List<String> lines = new ArrayList<>();
		for (T item : data) {
			lines.add(item.toString());
		}
		try {
			DataWriter.writeFile(filePath, lines);
			System.out.println("Данные успешно сохранены в файл.");
		} catch (IOException e) {
			System.out.println("Ошибка записи в файл: " + e.getMessage());
		}
	}
}