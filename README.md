# Notepad 2.0
An Enhanced version of notepad made using Java Swing

This project was made in Java 8 using notepad++.

# How to run
• Make sure you have jdk 1.8.0 or above version. (It might also work with version 1.6 & 1.7)  
• Use any IDE or command promt to run the file

# Screenshots
![home](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/567966a6-fe46-43e2-af44-e0010d1fc97f)
![new file](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/716c7805-5a04-46a0-b295-4d90a8607288)
![open](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/7144bce0-c207-45e1-8375-7d0e2ac0da7e)
![file name](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/7945931f-24d4-4ad7-abec-07f865003c10)
![menu](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/eca0609c-894a-46c6-84b4-ea47c1bbf7c0)
![color](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/929e5f20-12ca-49e2-b37b-21c0bf708296)
![popup](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/3a29a22e-6e3c-44b6-b83e-5d42f7fc6aea)
![font](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/06ccacd1-27da-4cb9-821d-e9952d36f8ff)
![about](https://github.com/ShadowSD10/Notepad2.0/assets/137081476/1dd60b63-6094-4351-bea7-26e597296a7f)

# Summary of this code

1. Import Statements:  
   ‣ The import statements include the necessary classes for Swing components, events, file handling, printing, and more.  
  
2. Class Declaration:  
   ‣ The class notepad2 extends the JFrame class and implements the ActionListener and ItemListener interfaces.  
   ‣ It represents the main frame of the Notepad application.  
 
3. Variable Declarations:  
   ‣ The code declares various variables, including components, colors, menus, menu items, icons, text areas, scroll panes, and managers.  

4. Constructor:  
   ‣ The constructor initializes the main frame of the Notepad application.  
   ‣ It sets the title, size, location, and icon of the frame.  
   ‣ It creates a JTextArea for text input and sets its properties.  
   ‣ It creates an UndoManager to handle undo and redo operations.  
   ‣ It creates a JScrollPane to provide scrollbars to the text area.  
   ‣ It calls methods to create the menu bar, set keyboard shortcuts, and set the popup menu.  
   ‣ It also creates additional frames for font selection and about information.  
   ‣ Finally, it sets the default font size and family, sets the frame icon, sets the default close operation, and makes the frame visible.  
   
5. Event Handling:  
   ‣ The actionPerformed method handles various actions performed by the user, such as clicking menu items or buttons.  
   ‣ It uses if-else statements to determine the source of the event and executes the corresponding actions.  
   ‣ Actions include creating a new file, opening a file, saving a file, printing, exiting the application, copying, cutting, pasting, undoing, redoing, selecting all text, changing font and word wrap settings, changing themes, and more.  
   ‣ Some actions involve using file choosers to select files and color choosers to select colors.  

6. Item State Change Handling:  
  ‣ The itemStateChanged method handles changes in selected items, specifically in the font, style, and size selection combo boxes.  
  ‣ It updates the sample text font and the size field accordingly.  
 
7. Menu Bar Creation:  
  ‣ The createMenuBar method creates the menu bar and adds menus and menu items to it.  
  ‣ Menus include File, Edit, Help, and Format.  
  ‣ Menu items are added to the corresponding menus and linked to the ActionListener for event handling.  
