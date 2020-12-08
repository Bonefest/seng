package src;

import src.EntityNotFoundException;
import src.Account;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Implement methods using Stream API
 */
public class AccountAnalytics {
    private Collection<Account> accounts;

    public static AccountAnalytics of(Collection<Account> accounts) {
        return new AccountAnalytics(accounts);
    }

    private AccountAnalytics(Collection<Account> accounts) {
        this.accounts = accounts;
    }

    /**
     * Returns {@link Optional} that contains an {@link Account} with the max value of balance
     *
     * @return account with max balance wrapped with optional
     */
    public Optional<Account> findRichestPerson() {
        return accounts.stream().max((x, y) -> x.getBalance().compareTo(y.getBalance()));
    }

    /**
     * Returns a {@link List} of {@link Account} that have a birthday month equal to provided.
     *
     * @param birthdayMonth a month of birth
     * @return a list of accounts
     */
    public List<Account> findAccountsByBirthdayMonth(Month birthdayMonth) {
        return accounts.stream().filter(x -> x.getBirthday().getMonth().equals(birthdayMonth)).collect(Collectors.toList());
    }

    /**
     * Returns a map that separates all accounts into two lists - male and female. Map has two keys {@code true} indicates
     * male list, and {@code false} indicates female list.
     *
     * @return a map where key is true or false, and value is list of male, and female accounts
     */
    public Map<Boolean, List<Account>> partitionMaleAccounts() {
    	Map<Boolean, List<Account>> account = new HashMap<Boolean, List<Account>>();
    	account.put(true, accounts.stream().filter(x -> x.getSex() == Sex.MALE).collect(Collectors.toList()));
    	account.put(false, accounts.stream().filter(x -> x.getSex() == Sex.FEMALE).collect(Collectors.toList()));
        
    	return account;
    }

    /**
     * Returns a {@link Map} that stores accounts grouped by its email domain. A map key is {@link String} which is an
     * email domain like "gmail.com". And the value is a {@link List} of {@link Account} objects with a specific email domain.
     *
     * @return a map where key is an email domain and value is a list of all account with such email
     */
    public Map<String, List<Account>> groupAccountsByEmailDomain() {
    	
    	List<String> domains = accounts.stream().map(x -> x.getEmail().split("@")[1]).distinct().collect(Collectors.toList());
    	
    	for(String s : domains) {
    		System.out.println(s);
    	}
    	
    	Map<String, List<Account>> groups = new HashMap<String, List<Account>>();
    	for(String domain : domains) {
    		groups.put(domain, accounts.stream().filter(x -> domain.equals(x.getEmail().split("@")[1])).collect(Collectors.toList()));
    		System.out.println(domain + " " + groups.get(domain).size());
    	}
    	
    	return groups;
    }

    /**
     * Returns a number of letters in all first and last names.
     *
     * @return total number of letters of first and last names of all accounts
     */
    public int getNumOfLettersInFirstAndLastNames() {
        return accounts.stream().mapToInt(x -> x.getFirstName().length() + x.getLastName().length()).reduce(0, (prev, x) -> x + prev);
    }

    /**
     * Returns a total balance of all accounts.
     *
     * @return total balance of all accounts
     */
    public BigDecimal calculateTotalBalance() {
    	return accounts.stream().map(x -> x.getBalance()).reduce(new BigDecimal(0), (prev, x) -> x.add(prev));
    }

    /**
     * Returns a {@link List} of {@link Account} objects sorted by first and last names.
     *
     * @return list of accounts sorted by first and last names
     */
    public List<Account> sortByFirstAndLastNames() {
        return accounts.stream().sorted( (x, y) -> (x.getFirstName() + x.getLastName()).compareTo(y.getFirstName() + y.getLastName())).collect(Collectors.toList());
    }

    /**
     * Checks if there is at least one account with provided email domain.
     *
     * @param emailDomain
     * @return true if there is an account that has an email with provided domain
     */
    public boolean containsAccountWithEmailDomain(String emailDomain) {
        return accounts.stream().anyMatch(x -> x.getEmail().split("@")[1].equals(emailDomain));
    }

    /**
     * Returns account balance by its email. Throws {@link EntityNotFoundException} with message
     * "Cannot find Account by email={email}" if account is not found.
     *
     * @param email account email
     * @return account balance
     */
    public BigDecimal getBalanceByEmail(String email) {
        List<Account> accs = accounts.stream().filter(x -> x.getEmail() == email).collect(Collectors.toList());
        if(accs.size() == 0)
        	throw new EntityNotFoundException("Cannot find Account by email=" + email);
        
        return accs.iterator().next().getBalance();
    }

    /**
     * Collects all existing accounts into a {@link Map} where a key is account id, and the value is {@link Account} instance
     *
     * @return map of accounts by its ids
     */
    public Map<Long, Account> collectAccountsById() {
        Map<Long, Account> map = new HashMap<Long, Account>();
        
        for(Account account : accounts) {
        	map.put(account.getId(), account);
        }
        
        return map;
    }

    /**
     * Filters accounts by the year when an account was created. Collects account balances by its emails into a {@link Map}.
     * The key is {@link Account#email} and the value is @link Account#balance}
     *
     * @param year the year of account creation
     * @return map of account by its ids the were created in a particular year
     */
    public Map<String, BigDecimal> collectBalancesByIdForAccountsCreatedOn(int year) {
        List<Account> accs = accounts.stream().filter(x -> x.getCreationDate().getYear() == year).collect(Collectors.toList());
        return accs.stream().collect(Collectors.toMap(Account::getEmail, Account::getBalance));
    }

    /**
     * Returns a {@link Map} where key is {@link Account#lastName} and values is a {@link Set} that contains first names
     * of all accounts with a specific last name.
     *
     * @return a map where key is a first name and value is a set of first names
     */
    public Map<String, Set<String>> groupFirstNamesByLastNames() {
        Map<String, Set<String>> result = new HashMap<String, Set<String>>();
        
        List<String> lastNames = accounts.stream().map(x -> x.getLastName()).distinct().collect(Collectors.toList());
        for (String ln : lastNames) {
        	result.put(ln, accounts.stream().filter(x -> x.getLastName().equals(ln)).map(x -> x.getFirstName()).distinct().collect(Collectors.toSet()));
        }
        
        return result;
    }

    /**
     * Returns a {@link Map} where key is a birthday month, and value is a {@link String} that stores comma and space
     * -separated first names (e.g. "Polly, Dylan, Clark"), of all accounts that have the same birthday month.
     *
     * @return a map where a key is a birthday month and value is comma-separated first names
     */
    public Map<Month, String> groupCommaSeparatedFirstNamesByBirthdayMonth() {
        List<Month> months = accounts.stream().map(x -> x.getBirthday().getMonth()).distinct().collect(Collectors.toList());
        
        HashMap<Month, String> result= new HashMap<Month, String>();
        
        for (Month m : months) {
        	result.put(m, accounts.stream().filter(x -> x.getBirthday().getMonth().equals(m)).map(x -> x.getFirstName()).reduce((prev, x) -> prev + ", " + x).get());
        }
        
        return result;
    }

    /**
     * Returns a {@link Map} where key is a {@link Month} of {@link Account#creationDate}, and value is total balance
     * of all accounts that have the same value creation month.
     *
     * @return a map where key is a creation month and value is total balance of all accounts created in that month
     */
    public Map<Month, BigDecimal> groupTotalBalanceByCreationMonth() {
    	
    	List<Month> months = accounts.stream().map(x -> x.getCreationDate().getMonth()).distinct().collect(Collectors.toList());
    	
        Map<Month, BigDecimal> result = new HashMap<Month, BigDecimal>();
        
    	for(Month m : months) {
    		result.put(m, accounts.stream().filter(x -> x.getCreationDate().getMonth().equals(m)).map(x -> x.getBalance()).reduce(new BigDecimal(0), (x, y) -> x.add(y)));
    	}
    	
    	return result;
    }

    /**
     * Returns a {@link Map} where key is a letter {@link Character}, and value is a number of its occurrences in
     * {@link Account#firstName}.
     *
     * @return a map where key is a letter and value is its count in all first names
     */
    public Map<Character, Long> getCharacterFrequencyInFirstNames() {
        String chars = accounts.stream().map(x -> x.getFirstName()).reduce((x, y) -> x + y).get();
        Map<Character, Long> result = new HashMap<Character, Long>();
        
        for(char c : chars.toCharArray()) {
        	result.put(c, chars.chars().map(x -> (char)x).filter(x -> x == c).count());
        }
        
        return result;
    }

    /**
     * Returns a {@link Map} where key is a letter {@link Character}, and value is a number of its occurrences ignoring
     * case, in all {@link Account#firstName} and {@link Account#lastName}. All letters should stored in lower case.
     *
     * @return a map where key is a letter and value is its count ignoring case in all first and last names
     */
    public Map<Character, Long> getCharacterFrequencyIgnoreCaseInFirstAndLastNames() {
        String chars = accounts.stream().map(x -> x.getFirstName() + x.getLastName()).reduce((x,  y) -> x + y).get().toLowerCase();
        
        Map<Character, Long> result = new HashMap<Character, Long>();
        
        for(char c: chars.toCharArray()) {
        	result.put(c, chars.chars().map(x -> (char)x).filter(x -> x == c).count());
        }
        
        return result;
    }

}

