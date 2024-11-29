package com.internship.aston_project;

import com.internship.aston_project.factory.BusFactory;
import com.internship.aston_project.factory.ObjectFactory;
import com.internship.aston_project.factory.StudentFactory;
import com.internship.aston_project.factory.UserFactory;
import com.internship.aston_project.sort.QuickSort;
import com.internship.aston_project.sort.SortStrategy;
import com.internship.aston_project.utils.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class AstonProjectApplication {

	public static void main(String[] args) {
		// Создание объекта для чтения ввода пользователя
		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		System.out.println("Добро пожаловать в программу управления данными!");
		// Главный цикл программы, позволяет пользователю выбрать тип данных
		while (running) {
			System.out.println("""
                Выберите тип данных:
                1. Автобусы
                2. Студенты
                3. Пользователи
                4. Выйти""");

			String typeChoice = scanner.nextLine();
			switch (typeChoice) {
				case "1" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new BusFactory());
				case "2" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new StudentFactory());
				case "3" -> manageData(new ArrayList<>(), new QuickSort<>(), scanner, new UserFactory());
				case "4" -> {
					System.out.println("Выход из программы. До свидания!");
					running = false; // Выход из программы
				}
				default -> System.out.println("Неверный выбор. Попробуйте снова.");
			}
		}
		scanner.close();
	}

	private static <T extends Comparable<T>> void manageData(List<T> data, SortStrategy<T> sortStrategy, Scanner scanner, ObjectFactory<T> factory) {
		// Создание объекта для бинарного поиска
		BinarySearch<T> binarySearch = new BinarySearch<>();
		boolean managing = true;

		// Определение типа данных для управления
		String type = null;
		switch (factory.getClass().getSimpleName()){
			case "StudentFactory" -> {type = "Student";}
			case "UserFactory" -> {type = "User";}
			case "BusFactory" -> {type = "Bus";}}

		// Цикл управления данными
		while (managing) {
			System.out.printf("Управление данными типа %s:%n", type);
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
						data = fillData(scanner, factory); // Заполнение данных
						System.out.println("Input data: " + data);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
				case "2" -> {
					if (data.isEmpty()) {
						System.out.println("Данные отсутствуют. Сначала заполните массив.");
					} else {
						// Выбор поля для сортировки и выполнение сортировки
							sortStrategy.sort(data);
							System.out.println("Данные успешно отсортированы.");
							System.out.println("Отсортированный массив: " + data);
					}
				}
				case "3" -> {
					if (data.isEmpty()) {
						System.out.println("Данные отсутствуют. Сначала заполните массив.");
					} else {
						// Выбор поля для поиска и выполнение бинарного поиска
						Function<T, ? extends Comparable> searchKey = SearchField.chooseField(scanner, factory);
						if (searchKey != null) {
							System.out.println("Введите ключ для поиска:");
							String searchInput = scanner.nextLine();
							T target = factory.createForSearch(searchInput);
							if (target != null) {
								List<T> foundItems = binarySearch.searchAll(data, target, searchKey);
								if (!foundItems.isEmpty()) {
									System.out.println("Найденные элементы:");
									for (T item : foundItems) {
										System.out.println(item);
									}
								} else {
									System.out.println("Элементы не найдены.");
								}
							}
						}
					}
				}
				case "4" -> {
					if (data.isEmpty()) {
						System.out.println("Данные отсутствуют. Сначала заполните массив.");
					} else {
						// Сохранение данных в файл
						saveDataToFile(PropertiesLoader.getAddressBasedOnType(factory.getClass().getSimpleName()), factory, data);
					}
				}
				case "5" -> managing = false; // Возврат в главное меню
				default -> System.out.println("Неверный выбор. Попробуйте снова.");
			}
		}
	}

	private static <T extends Comparable<T>> List<T> fillData(Scanner scanner, ObjectFactory<T> factory) throws IOException {
		List<T> data = new ArrayList<>();
		System.out.println("""
			Выберите способ заполнения:
			1. Вручную
			2. Из файла
			3. Рандомно""");

		String choice = scanner.nextLine();
		switch (choice) {
			case "1" -> {
				// Заполнение данных вручную
				System.out.println("Введите количество элементов:");
				String countInput = scanner.nextLine();
				if (Validator.isValidInteger(countInput)) {
					int count = Integer.parseInt(countInput);
					for (int i = 0; i < count; i++) {
						System.out.printf("Введите данные для элемента %d:%n", i + 1);
						T item = factory.create(scanner, choice);
						if (item != null) {
							data.add(item);
						} else {
							System.out.println("Ошибка ввода. Повторите.");
							i--;
						}
					}
					System.out.println("Данные успешно добавлены.");
				} else {
					System.out.println("Ошибка: введите целое число.");
				}
			}
			case "2" -> {
				// Загрузка данных из файла
				List<String> listFromFile = FileUtils.readFile(PropertiesLoader.getAddressBasedOnType(factory.getClass().getSimpleName()));

				for (String line : listFromFile) {
					String formattedInput = FileUtils.parseLineByType(line, factory.getClass().getSimpleName());

					try (Scanner lineScanner = new Scanner(formattedInput)) {
						T object = factory.create(lineScanner, choice);
						if (object != null) {
							data.add(object);
						}
					}
				}
				if (data.isEmpty()) {
					System.out.println("Ошибка: Данные не загружены из файла.");
				}else {
					System.out.println("Данные успешно загружены из файла.");
				}
			}

			case "3" -> {
				// Генерация случайных данных
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


	private static <T extends Comparable<T>> void saveDataToFile(String filePath, ObjectFactory<T> factory, List<T> data) {
		// Сохранение данных в файл
		try {
			FileUtils.prepareAndWrite(filePath, factory.getClass().getSimpleName(), data);
			System.out.println("Данные успешно сохранены в файл.");
		} catch (IOException e) {
			System.out.println("Ошибка записи в файл: " + e.getMessage());
		}
	}
}
