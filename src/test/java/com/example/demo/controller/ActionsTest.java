package com.example.demo.controller;

import junit.framework.TestCase;
import com.example.demo.model.Account;
import com.example.demo.model.Contact;
import com.example.demo.model.Lead;
import com.example.demo.model.Opportunity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static com.example.demo.model.Opportunity.Status.CLOSED_LOST;

public class ActionsTest extends TestCase {

    protected HashMap<Integer, Lead> leadMap = new HashMap<>();
    private HashMap<Integer, Contact> contactMap = new HashMap<>();
    private HashMap<Integer, Opportunity> opportunityMap = new HashMap<>();
    private HashMap<Integer, Account> accountMap = new HashMap<>();

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

//    @BeforeEach
//    public void setUp() {
//        System.setOut(new PrintStream(outputStreamCaptor));
//    }
//    @AfterEach
//    public void tearDown() {
//        System.setOut(standardOut);
//    }

    //no need to test runApp() because it only prints the menu.
    @DisplayName("runApp displays the menu correctly")
    @Test
    public void testRunApp(){
        System.setOut(new PrintStream(outputStreamCaptor));
        Actions actions = new Actions(false);
        actions.setAppIsOn(true);
        InputStream exitInput = new ByteArrayInputStream("exit".getBytes());

        //prints menu and recognizes exit input
        String menu = "New Lead             \n" +
                "Show Leads           \n" +
                "Lookup Lead id       \n" +
                "Convert id           \n" +
                "Show Opportunities   \n" +
                "Close-Lost id        \n" +
                "Close-Won id         \n" +
                "Exit               \n";
        actions.runApp(exitInput);
        assertTrue(outputStreamCaptor.toString().contains(menu));

        //recognizes exit input and exits application
        String exitText = "Closing app...";
        assertTrue(outputStreamCaptor.toString().contains(exitText));

        System.setOut(standardOut);

    }

    // Second method testExtractIdFromElection analyzes input and returns one of three inputs
    @Test
    public void testExtractIdFromElection(){
        Actions actions = new Actions(false);
        //incorrect input (for example "pepa pig")
        assertEquals("incorrect input", actions.extractIdFromElection("pepa pig"));

        //correct input (for example "convert dsa") but the id does not exist
        assertEquals("invalid id", actions.extractIdFromElection("convert dsa"));

        //correct input and correct id (method removes id when valid and returns valid command)
        assertEquals("convert", actions.extractIdFromElection("convert 0"));
    }


    @DisplayName("Create Lead correctly")
    @Test
    public void testCreateLead() {
        Lead test = new Lead("test", "987654321","test@test.com", "tester SA" );
        leadMap.put(0, test);

        assertEquals(1, leadMap.size());
    }
    /*
    @DisplayName("Lists Leads only if there are leads in the map")
    @Test
    public void testListLeads() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Actions actions = new Actions(false);
        //if there are no leads (setter with empty lead list)
        actions.setLeadsMap(new HashMap<>());
        actions.listLeads();
        assertEquals("There are currently no leads to display!", outputStreamCaptor.toString().trim());
        System.setOut(standardOut);
    }

    @DisplayName("Lists Opportunities only if there are opporutnities in the map")
    @Test
    public void testListOpportunities() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Actions actions = new Actions(false);
        //if there are no leads (setter with empty lead list)
        actions.setOpportunityMap(new HashMap<>());
        actions.listOpportunities();
        assertEquals("There are currently no opportunities to display!", outputStreamCaptor.toString().trim());
        System.setOut(standardOut);
    }

    @DisplayName("Lists unique leads")
    @Test
    public void testListUniqueLead() {
        System.setOut(new PrintStream(outputStreamCaptor));
        Actions actions = new Actions(false);
        //we create new lead and put it to hashmap
        Lead rakion = new Lead("Rakion", "123456789", "rakion@gmail.com", "Rakion SA");
        HashMap<Integer, Lead> leadsList = new HashMap<>();
        leadsList.put(rakion.getId(), rakion);
        actions.setLeadsMap(leadsList);
        //we feed the id to intId and execute method listUniqueLead to see if it finds the lead with its id
        actions.setIntId(rakion.getId());
        actions.listUniqueLead();
        String rakionInfo = "0          Rakion       123456789          rakion@gmail.com                 Rakion SA";
        assertTrue(outputStreamCaptor.toString().contains(rakionInfo));
        System.setOut(standardOut);
    }
    @DisplayName("Convert a Lead")
    @Test
    public void testConvertId() {
        Lead rakion = new Lead("Rakion", "123456789", "rakion@gmail.com", "Rakion SA");

        leadMap.put(0, rakion);
        //Crea contacto con los datos del Lead a convertir
        Contact contact = new Contact(leadMap.get(0));
        contactMap.put(contact.getId(), contact);

        //Crea la oportunidad referida al contacto
        Opportunity opportunity = new Opportunity("Hybrid", 40, contact);
        opportunityMap.put(opportunity.getId(), opportunity);

        //Crea la cuenta referida a esta conversión
        Account account = new Account("Produce", 40,"Barcelona", "Spain");
        accountMap.put(account.getId(), account);

        //Borra el lead de la lista de Leads
        leadMap.remove(0);

        //Analizamos cuantos elementos hay en cada lista
        assertEquals(0, leadMap.size());
        assertEquals(1, contactMap.size());
        assertEquals(1, opportunityMap.size());
        assertEquals(1, accountMap.size());
    }

    @DisplayName("Change Opportunity Status")
    @Test
    public void testChangeOpportunityStatus(){
        Actions actions = new Actions(false);

        //create lead, contact and opportunity
        Lead rakion = new Lead("Rakion", "123456789", "rakion@gmail.com", "Rakion SA");
        leadMap.put(0, rakion);
        Contact contact = new Contact(leadMap.get(0));
        contactMap.put(contact.getId(), contact);
        Opportunity opportunity = new Opportunity("Hybrid", 40, contact);
        opportunityMap.put(opportunity.getId(), opportunity);

        //store opportunity map in actions instance
        actions.setOpportunityMap(opportunityMap);
        actions.setIntId(0);

        //call actions method to change status and verify
        actions.changeOpportunityStatus("lost");

        assertEquals(CLOSED_LOST, opportunityMap.get(0).getStatus());
    }

    @DisplayName("tests the split method that displays the questions and verifies the answers")
    @Test
    public void testGetInputDataAndIsCorrect() {
        Actions actions = new Actions(false);
        assertTrue(actions.isCorrect("\nEnter the phone number of the lead: ", "123456789"));
        assertFalse(actions.isCorrect("\nEnter the phone number of the lead: ", "12354678910"));

        assertTrue(actions.isCorrect("\nEnter the lead contact e-mail: ", "abcdefg@hotmail.com"));
        assertFalse(actions.isCorrect("\nEnter the lead contact e-mail: ", "abcdefg@"));

        assertTrue(actions.isCorrect("Which vehicle is the client interested in? HYBRID, FLATBED o BOX: \n", "flatbed"));
        assertFalse(actions.isCorrect("Which vehicle is the client interested in? HYBRID, FLATBED o BOX: \n", "mixto"));

        assertTrue(actions.isCorrect("Enter the number of employees: \n", "50"));
        assertFalse(actions.isCorrect("Enter the number of employees: \n", "abcdef"));
    }
*/
}