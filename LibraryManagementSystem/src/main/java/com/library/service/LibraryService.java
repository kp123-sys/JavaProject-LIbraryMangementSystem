package com.library.service;

import com.library.model.Book;
import com.library.model.IssueRecord;
import com.library.model.Member;
import com.library.util.DataManager;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LibraryService {
    private static final String BOOKS_FILE = "books.dat";
    private static final String MEMBERS_FILE = "members.dat";
    private static final String ISSUES_FILE = "issues.dat";

    private List<Book> books;
    private List<Member> members;
    private List<IssueRecord> issues;

    public LibraryService() {
        this.books = DataManager.loadData(BOOKS_FILE);
        this.members = DataManager.loadData(MEMBERS_FILE);
        this.issues = DataManager.loadData(ISSUES_FILE);
    }

    // --- Book Operations ---
    public void addBook(Book book) {
        if (getBookById(book.getBookId()).isPresent()) {
            throw new IllegalArgumentException("Book ID already exists.");
        }
        books.add(book);
        saveBooks();
    }

    public void updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId().equals(updatedBook.getBookId())) {
                books.set(i, updatedBook);
                saveBooks();
                return;
            }
        }
        throw new IllegalArgumentException("Book not found.");
    }

    public void deleteBook(String bookId) {
        books.removeIf(b -> b.getBookId().equals(bookId));
        saveBooks();
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(String id) {
        return books.stream().filter(b -> b.getBookId().equals(id)).findFirst();
    }

    // --- Member Operations ---
    public void addMember(Member member) {
        if (getMemberById(member.getMemberId()).isPresent()) {
            throw new IllegalArgumentException("Member ID already exists.");
        }
        members.add(member);
        saveMembers();
    }

    public void updateMember(Member updatedMember) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getMemberId().equals(updatedMember.getMemberId())) {
                members.set(i, updatedMember);
                saveMembers();
                return;
            }
        }
        throw new IllegalArgumentException("Member not found.");
    }

    public void deleteMember(String memberId) {
        members.removeIf(m -> m.getMemberId().equals(memberId));
        saveMembers();
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public Optional<Member> getMemberById(String id) {
        return members.stream().filter(m -> m.getMemberId().equals(id)).findFirst();
    }

    // --- Issue Operations ---
    public void issueBook(String bookId, String memberId) {
        Optional<Book> bookOpt = getBookById(bookId);
        Optional<Member> memberOpt = getMemberById(memberId);

        if (bookOpt.isEmpty()) throw new IllegalArgumentException("Book not found.");
        if (memberOpt.isEmpty()) throw new IllegalArgumentException("Member not found.");
        
        Book book = bookOpt.get();
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is currently unavailable.");
        }

        book.setAvailable(false);
        saveBooks();

        String issueId = UUID.randomUUID().toString().substring(0, 8);
        IssueRecord record = new IssueRecord(issueId, bookId, memberId, LocalDate.now());
        issues.add(record);
        saveIssues();
    }

    public void returnBook(String bookId) {
        Optional<IssueRecord> issueOpt = issues.stream()
                .filter(i -> i.getBookId().equals(bookId) && !i.isReturned())
                .findFirst();

        if (issueOpt.isEmpty()) {
            throw new IllegalArgumentException("Active issue record for this book not found.");
        }

        IssueRecord record = issueOpt.get();
        record.setReturned(true);
        record.setReturnDate(LocalDate.now());
        saveIssues();

        Optional<Book> bookOpt = getBookById(bookId);
        bookOpt.ifPresent(b -> {
            b.setAvailable(true);
            saveBooks();
        });
    }

    public List<IssueRecord> getAllIssues() {
        return issues;
    }

    // --- Persistence ---
    private void saveBooks() {
        DataManager.saveData(books, BOOKS_FILE);
    }

    private void saveMembers() {
        DataManager.saveData(members, MEMBERS_FILE);
    }

    private void saveIssues() {
        DataManager.saveData(issues, ISSUES_FILE);
    }
}
