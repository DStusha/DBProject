package com.company;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static void printMenu() {
        System.out.println();
        System.out.println("Меню: ");
        System.out.println("1 - Получение всех данных из таблицы");
        System.out.println("2 - Получение данных по ФИО");
        System.out.println("3 - Получение данных по дате рождения");
        System.out.println("4 - Добавить в основную таблицу");
        System.out.println("5 - Удалить из основной по ФИО");
        System.out.println("6 - Удалить из основной по дате рождения");
        System.out.println("7 - Обновление ФИО в основной таблице для записей с указанным ФИО");
        System.out.println("8 - Обновление даты рождения в основной таблице для записей с указанной датой рождения");
        System.out.println("9 - Копирование всех данных из основной таблицы в резервную");
        System.out.println("10 - Очистка таблицы");
        System.out.println("0 - Выход");
        System.out.println();
    }

    public static void main(String[] args) {
        PeopleDAO peopleDAO = null;
        try {
            peopleDAO = new PeopleDAO();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner(System.in);
        ArrayList<People> list = new ArrayList<People>();
        int i;
        int k;
        do{
            printMenu();
            i = scan.nextInt();
            scan.nextLine();
            switch (i){
                case 1:
                    System.out.println("1 - основная таблица, 2 - резервная таблица ");
                    list = peopleDAO.getAllPeople(scan.nextInt());
                    for(People p : list){
                        p.print();
                    }
                    break;
                case 2:
                    System.out.println("1 - основная. 2 - резервная");
                    k = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Введите ФИО");
                    list = peopleDAO.getPeopleByFIO(k, scan.nextLine());
                    if(list.isEmpty()){
                        System.out.println("ФИО не найдено");
                    }
                    for(People p : list){
                        p.print();
                    }
                    break;
                case 3:
                    System.out.println("1 - основная. 2 - резервная");
                    k = scan.nextInt();
                    scan.nextLine();
                    System.out.println("Введите дату рождения");
                    list = peopleDAO.getPeopleByBirth(k, Date.valueOf(scan.nextLine()));
                    if(list.isEmpty()){
                        System.out.println("Дата рождения не найдена");
                    }
                    for(People p : list){
                        p.print();
                    }
                    break;

                case 4:
                    People p = new People();
                    System.out.println("Введите ФИО для добавления");
                    p.setFullName(scan.nextLine());
                    System.out.println("Введите дату рождения для добавления данных");
                    p.setDateOfBirth(Date.valueOf(scan.nextLine()));
                    peopleDAO.addToMainTable(p);
                    break;
                case 5:
                    System.out.println("Введите ФИО для удаления данных");
                    String fio = scan.nextLine();
                    if (peopleDAO.getPeopleByFIO(1,fio).isEmpty()){
                        System.out.println("ФИО не найдено");
                    }else {
                        peopleDAO.deleteByFullName(fio);
                    }
                    break;
                case 6:
                    System.out.println("Введите дату рождения для удаления данных");
                    Date date = Date.valueOf(scan.nextLine());
                    if (peopleDAO.getPeopleByBirth(1,date).isEmpty()){
                        System.out.println("Дата не найдена");
                    }else {
                        peopleDAO.deleteByBirth(date);
                    }
                    break;
                case 7:
                    System.out.println("Введите старое значение ФИО");
                    fio = scan.nextLine();
                    if (peopleDAO.getPeopleByFIO(1,fio).isEmpty()){
                        System.out.println("ФИО не найдено");
                    }else {
                        System.out.println("Введите новое значение ФИО");
                        String newFio = scan.nextLine();
                        peopleDAO.updateFullName(fio, newFio);
                    }
                    break;
                case 8:
                    System.out.println("Введите старую дату рождения");
                    date = Date.valueOf(scan.nextLine());
                    if (peopleDAO.getPeopleByBirth(1,date).isEmpty()){
                        System.out.println("Дата не найдена");
                    }else {
                        System.out.println("Введите новую дату рождения");
                        Date newDate = Date.valueOf(scan.nextLine());
                        peopleDAO.updateDateOfBirth(date, newDate);
                    }
                    break;
                case 9:
                    peopleDAO.copyToReserve();
                    break;
                case 10:
                    System.out.println("1 - основная, 2 - резервная");
                    peopleDAO.clearTable(scan.nextInt());
                    break;
                case 0:
                    System.out.println("Вы вышли");
                    System.exit(0);
                    break;
            }
        }while(i!=0);

    }
}
