package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Actions {

    protected HashMap<Integer, Lead> leadsMap = new HashMap<>();
    private HashMap<Integer, Contact> contactMap = new HashMap<>();
    private HashMap<Integer, Opportunity> opportunityMap = new HashMap<>();
    private HashMap<Integer, Account> accountMap = new HashMap<>();

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    LeadRepository leadRepository;
    @Autowired
    OpportunityRepository opportunityRepository;
    @Autowired
    SalesRepRepository salesRepRepository;

    private boolean appIsOn;
    private int intId;

    //////////////////////////////////////////////////
    //extra constructor, setters & getters
    //constractor with just a boolean so the tests don't execute the app
    public Actions(boolean appIsOn) {
        this.appIsOn = appIsOn;
    }
    //setter for testing purposes
    public void setLeadsMap(HashMap<Integer, Lead> leadsMap) {
        this.leadsMap = leadsMap;
    }
    public void setAppIsOn(boolean appIsOn) {
        this.appIsOn = appIsOn;
    }
    public void setOpportunityMap(HashMap<Integer, Opportunity> opportunityMap) {
        this.opportunityMap = opportunityMap;
    }
    public void setIntId(int intId) {
        this.intId = intId;
    }

    //////////////////////////////////////////////
    public Actions() {
        appIsOn = true;
        //Abrir los datos guardados en el CSV(Base de datos guardados de la ultima sesion)
        runApp(System.in);

    }

    public void runApp(InputStream inputStream) {
        String menu = "What do you want to do?\n\n" +
                "New Lead             \n" +
                "New SalesRep         \n" +
                "Show Leads           \n" +
                "Lookup Lead id       \n" +
                "Convert id           \n" +
                "Show Opportunities   \n" +
                "Close-Lost id        \n" +
                "Close-Won id         \n" +
                "Exit               \n\n" +
                "Please write your answer here:";
        while (appIsOn) {
            System.out.println(menu);
            Scanner scan = new Scanner(inputStream);
            String election = scan.nextLine().toLowerCase();
            election = extractIdFromElection(election);
            //he comentado los métodos. Los vamos descomentando a medida que los testeemos / estén listos
            switch (election) {
                case "new lead":
                    createLead();
                    break;
                case "new salesrep":
                    createSalesRep();

                    break;
                case "show leads":
                    listLeads();
                    break;
                case "lookup lead":
                    listUniqueLead();
                    break;
                case "convert":
                    convertId();
                    break;
                case "show opportunities":
                    listOpportunities();
                    break;
                case "close-lost":
                case "close-won":
                    changeOpportunityStatus(election);
                    break;
                case "report Lead by salesrep":
                    // funcion que devuelve el numero de Lead de cada SalesRep


                    break;
                case "report opportunity by salesrep":
                    // funcion que devuelve el numero de oportunidades de cada SalesRep


                    break;
                case "report closed-won by salesrep":
                    // funcion que devuelve el numero de closed-won por cada SalesRep


                    break;


                case "exit":
                    appIsOn = false;
                    System.out.println("Saving data...");
                    //Crear CSV con los datos
                    System.out.println("Closing app...");
                    break;
                default:
                    //volver al inicio pq no coincide
                    System.out.println("That was not a valid answer! Please write one of the options!");
            }
        }
    }

    public String extractIdFromElection(String userInput) {
        try {
            if (userInput.matches("^(lookup lead|convert|close-lost|close-won)\s.+")) {
                String[] userInputSplit = userInput.split(" ");
                intId = Integer.parseInt(userInputSplit[(userInputSplit.length - 1)]);
                //deletes last word from userInput (from character 0 to last word obtained with lastindexOf)
                return userInput.substring(0, userInput.lastIndexOf(" "));
            } else if (userInput.matches("^(new lead|new salesrep|show leads|show opportunities|exit)")) {
                return userInput;
            }
            return "incorrect input";
        } catch (Throwable exception) {
            System.err.println("" +
                    "\n#################################################\n" +
                    "Invalid id! Please remember id must be a number!\n" +
                    "#################################################\n");
            return "invalid id";
        }
    }

    public void createLead() {
        System.out.println("Please enter the values for the lead: name, phone number, e-mail and company name");
        List<String> clientData = getInputData("\nEnter the client's name:",
                "\nEnter the phone number of the lead: ",
                "\nEnter the lead contact e-mail: ",
                "\nEnter the name of the company:",
                "\nEnter the name of the SalesRep:");


        SalesRep salesRep = salesRepRepository.findSalesRepsByName(clientData.get(4));
        Lead lead = new Lead(clientData.get(0), clientData.get(1), clientData.get(2), clientData.get(3),  salesRep);
        leadsMap.put(lead.getId(), lead);
        leadRepository.save(lead);
    }

    public void createSalesRep(){
        System.out.println("Please enter the values for the SalesRep: name");
        List<String> clientData = getInputData("\nEnter the client's name:");

        SalesRep salesRep = new SalesRep(clientData.get(0));

        salesRepRepository.save(salesRep);

    }

    public void listLeads() {
        if (!leadsMap.isEmpty()) {
            System.out.println("\n-------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %15s %25s %25s %25s %25s", "ID", "Name", "Phone number", "Email", "Company", "SalesRep\n");
            for (Lead lead : leadsMap.values()) {
                System.out.printf("%5s %15s %25s %25s %25s", lead.getId(), lead.getName(), lead.getPhoneNumber(), lead.getEmail(), lead.getCompanyName() + lead.getSalesRep() + "\n");
            }
            System.out.println("\n");
        } else {
            System.out.println("There are currently no leads to display!");
        }

        if(!leadRepository.findAll().isEmpty()){
            System.out.println("\n-------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %15s %25s %25s %25s", "ID", "Name", "Phone number", "Email", "Company\n");
            for (Lead lead : leadRepository.findAll()) {
                System.out.printf("%5s %15s %25s %25s %25s", lead.getId(), lead.getName(), lead.getPhoneNumber(), lead.getEmail(), lead.getCompanyName() + lead.getSalesRep() + "\n");
            }
            System.out.println("\n");
        } else {
            System.out.println("There are currently no leads to display!");
        }
    }

    public void listOpportunities() {
        if (!opportunityMap.isEmpty()) {
            System.out.println("\n-------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %15s %25s %25s %25s", "ID", "Product", "Quantity", "Decision Maker", "Status\n");
            for (Opportunity opportunity : opportunityMap.values()) {
                System.out.printf("%5s %15s %25s %25s %25s", opportunity.getId(), opportunity.getProduct(), opportunity.getQuantity(), opportunity.getDecisionMaker().getName(), opportunity.getStatus() + "\n");
            }
            System.out.println("\n");
        } else {
            System.out.println("There are currently no opportunities to display!");
        }
    }

    public void listUniqueLead() {
        try {
            Lead leadSel = leadsMap.get(intId);
            int leadId = leadSel.getId();
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.printf("%5s %15s %15s %25s %25s", "ID", "Name", "Phone number", "Email", "Company\n");
            System.out.printf("%5s %15s %15s %25s %25s", leadId, leadSel.getName(), leadSel.getPhoneNumber(), leadSel.getEmail(), leadSel.getCompanyName());
            System.out.println("\n\n");
        } catch (Throwable exception) {
            System.err.println("" +
                    "\n######################################################\n" +
                    "The id was not found! Please enter a valid id number!\n" +
                    "######################################################\n");
        }
    }

    public void convertId() {  //Crea el contacto, oportunidad y cuenta
        try {
            Lead lead = leadsMap.get(intId);

            Lead lead1 = leadRepository.getLeadById(intId);

            // CREA EL CONTACTO
            Contact contact = new Contact(lead);
            contactMap.put(contact.getId(), contact);

            Contact contact1 = new Contact(lead1);
            contactRepository.save(contact1);

            // CREA LA OPORTUNIDAD
            List<String> opportunityData = getInputData("Which vehicle is the client interested in? HYBRID, FLATBED o BOX: \n", "How many of them? \n");

            SalesRep salesRep = lead.getSalesRep();
            Opportunity opportunity = new Opportunity(opportunityData.get(0), Integer.parseInt(opportunityData.get(1)), contact, salesRep);
            opportunityMap.put(opportunity.getId(), opportunity);

            SalesRep salesRep1 = lead1.getSalesRep();
            Opportunity opportunity1 = new Opportunity(opportunityData.get(0), Integer.parseInt(opportunityData.get(1)), contact, salesRep1);
            opportunityRepository.save(opportunity1);



            // CREA LA CUENTA
            System.out.println("Please add some new data about the client");
            List<String> accountData = getInputData("Enter the company industry (Produce, ecommerce, manufacturing, medical or other): \n",
                    "Enter the number of employees: \n",
                    "Enter the city: \n",
                    "Enter the country: \n");
//
            Account account = new Account(accountData.get(0).toLowerCase(), Integer.parseInt(accountData.get(1)), accountData.get(2), accountData.get(3));
            accountMap.put(account.getId(), account);

            accountRepository.save(account);



            //BORRA LEAD
            leadsMap.remove(intId);

            leadRepository.deleteById(intId);


            //comprobación
            System.out.println(opportunity.getId() + " " + opportunity.getProduct() + " " + opportunity.getQuantity());
            System.out.println(account.getIndustry() + " " + account.getEmployeeCount() + " " + account.getCity() + " " + account.getCountry());
        } catch (Throwable exception) {
            System.err.println("" +
                    "\n######################################################\n" +
                    "The id was not found! Please enter a valid id number!\n" +
                    "######################################################\n");
        }
    }

    public void changeOpportunityStatus(String election) {
        try {
            Opportunity opportunity = opportunityMap.get(intId);
            opportunity.setStatus(election);
        } catch (Throwable exception) {
            System.err.println("" +
                    "\n######################################################\n" +
                    "The id was not found! Please enter a valid id number!\n" +
                    "######################################################\n");
        }

    }

    public List<String> getInputData(String... questions) {
        Scanner scanner = new Scanner(System.in);
        List<String> inputData = new ArrayList<>();
        for (String question : questions) {
            String answer = "";
            //Answers are checked for specific parameters depending on the question on method isCorrect
            //if answer is correct, it does "while false" and continues to next question.
            //if answer is incorrect, it does "while true" and repeats printing the question.
            do {
                System.out.println(question);
                answer = scanner.nextLine();
            } while (!isCorrect(question, answer));
            inputData.add(answer);
        }
        return inputData;
    }
    public boolean isCorrect(String question, String answer) {
        //depending on the question, it must comply with certain parameters to be a valid answer (and return true)
        switch (question) {
            case "\nEnter the phone number of the lead: ":
                try {
                    Integer.parseInt(answer);
                    if (answer.matches("[0-9]{9}")) {
                        return true;
                    } else {
                        System.err.println("Incorrect phone format! Phone number must only contain nine digits");
                        return false;
                    }
                } catch (Throwable exception) {
                    System.err.println("Invalid phone number! Please only insert numbers.");
                    return false;
                }
            case "\nEnter the lead contact e-mail: ":
                if (answer.matches("^(.+)@(\\S+)$")) {
                    return true;
                } else {
                    System.err.println("The e-mail was invalid. Please write a valid e-mail.");
                    return false;
                }
            case "Which vehicle is the client interested in? HYBRID, FLATBED o BOX: \n":
                if (answer.matches("hybrid|flatbed|box")) {
                    return true;
                } else {
                    System.err.println("That was not a valid vehicle! Please introduce either HYBRID, FLATBED o BOX");
                    return false;
                }
            case "How many of them? \n":
            case "Enter the number of employees: \n":
                try {
                    Integer.parseInt(answer);
                    return true;
                } catch (Throwable exception) {
                    System.err.println("That is not a number! Please only insert numbers.");
                    return false;
                }
            default:
                return true;
        }
    }
}
