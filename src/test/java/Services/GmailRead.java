package Services;

import Utility.ConfigFile;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.SubjectTerm;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GmailRead {
    public String getVerificationCode() {
        // Gmail account credentials
        //String username = "testautomationuserleap@gmail.com";
        //String password = "minn tgqt sdec eucj";

        ConfigFile config = new ConfigFile();
        String email = config.GetProperty("userid");
        String password = "uhyk qyjo kyhy bhst";

        // Properties for the Gmail server
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        String Code = "";


        try {
            // Create a session with the Gmail server
            //Session session = Session.getInstance(props);
            Session session = Session.getInstance(props,null);

            // Connect to the Gmail inbox
            Store store = session.getStore();
            store.connect(email,password);
            //store.connect(email, password);

            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Search for emails with a specific subject
            String subjectToSearch = "Your verification code";
            Message[] messages = inbox.search(new SubjectTerm(subjectToSearch), inbox.getMessages());
            Message mostRecentMessage = getMostRecentEmail(messages);
            Object content = mostRecentMessage.getContent();
            String textContent = extractTextFromMimeMultipart(content);

            // Print the extracted content
            System.out.println("Extracted Content: " + textContent);
            //System.out.println(extractCodeFromEmail(textContent));
            Code = extractCodeFromEmail(textContent);

            // Close the inbox and store
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Code;
    }

    // Helper method to get the most recent email among the given messages
    private static Message getMostRecentEmail(Message[] messages) throws  MessagingException {
        Message mostRecent = null;
        Date mostRecentDate = null;

        for (Message message : messages) {
            Date receivedDate = message.getReceivedDate();

            if (mostRecentDate == null || receivedDate.after(mostRecentDate)) {
                mostRecentDate = receivedDate;
                mostRecent = message;
            }
        }

        return mostRecent;
    }

    // Extract text from MIME multipart content
    private static String extractTextFromMimeMultipart(Object content) throws MessagingException, IOException {
        if (content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            int partCount = multipart.getCount();
            StringBuilder textContent = new StringBuilder();

            for (int i = 0; i < partCount; i++) {
                BodyPart part = multipart.getBodyPart(i);
                String disposition = part.getDisposition();

                if (disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT))) {
                    // Skip attachments
                    continue;
                } else {
                    // Extract and append text content
                    textContent.append(part.getContent().toString());
                }
            }

            return textContent.toString();
        } else if (content instanceof String) {
            // Content is already a string
            return (String) content;
        } else {
            // Handle other content types as needed
            return "";
        }
    }

    private static String extractCodeFromEmail(String emailContent) {
        // Implement your code extraction logic here, e.g., using regular expressions
        // Example: Extract a 6-digit code
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(emailContent);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
