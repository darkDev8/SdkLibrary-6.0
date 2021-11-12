<div align="center" style="margin : 20px">
 <img src="/img/api.png" alt="API" width="100px" height="100px">
 
 <h1 style="text-decoration: none"> SdkLibrary 6.0 👑💙 </h1>
 
</div>

</br>
SdkLibrary helps people to develop java software faster and easier.
</br>

Feature | Description
------------ | ------------
Managing the database | Connecting and managing databases
I/O operations | Open and manipulate files from computer
Data types and structures | New data structures and tools
Networking | Detect networks and utilities
Security tools | Hashing, encryption, serialization 
Operating system utilities | System processes, screen shot, ...

</br>

<details>
  <summary>Table of Contents</summary>
  <ul>
    <li><a href="#changes">Changes</a></li>
    <li><a href="#fixed-bugs">Fixed bugs</a></li>
    <li><a href="#new-methods">New methods</a></li>
    <li><a href="#removed-methods">Removed methods</a></li>
    <li><a href="#new-classes">New classes</a></li>
  </ul>
</details>

</br>

<h3 id="changes"> come here </h4>

</br>

</br>

</br>

## Changesss
---------------------------
* Change console background colors names.
* Change OperatingSystem class name to OSTools.
* Change isMac method name to isMacOs in OSTools.
* FileDialog and MessageBox no longer inheritance Swing class.
* MessageBox class supports frame as parent and icon.
* Better and more complete documentation of methods and classes.
* New classes for exporting tables with more features.

## Fixed bugs
---------------------------
* MessageBox background color.
</br>

## New methods
---------------------------
* setWindowCenter (Swing)					-> Set window(frame or dialog) to the center of screen.
* enableWindowEscClose (Swing)				-> Make window(frame or dialog) sensitive to ESC key and close by ESC.
* makeWindowMovable (Swing)					-> Make window(frame or dialog) movable from anywhere on window except some components.
* countTableRowsColumns (Swing)				-> Count the JTable rows or columns.
* getComponentPosition (Swing)				-> Get component position in the window.
* clearWindowComponents (Swing)				-> Clears text fields, labels and combo boxes in a form, dialog or panel.
* setWindowComponentsDirection (Swing)		-> Change all components direction in a window or panel.
* show (MessageBox)							-> Show the message box with title, message and message type.
* setColumnHeaderPosition(Tables)			-> Set the table column header text position.
* executeCommand (OSTools)					-> Execute a command in terminal and print output to text area.
* Constructor (ArchiveFile)					-> Constructor of ArchiveFile class(Supports compression levels).
* export (Exporter)							-> Export excel, pdf, json, ... files from a JTable object.
* countItemsInText	(Strings)				-> Count words, characters, lines and spaces in the input text.
* isKeyboardPersian	(OSTools)				-> Check the keyboard layout to be persian.
* isKeyboardEnglish (OSTools)				-> Check the keyboard layout to be english.
* getKeyboardLayout	(OSTools)				-> Get system current keyboard layout.

## Removed methods
---------------------------
* setFrameCenter (Swing)
* setDialogCenter (Swing)
* setFrameCloseEsc (Swing)
* setDialogCloseEsc (Swing)
* makeDialogMovable (Swing)
* makeFrameMovable (Swing)
* setTableDataModel (Swing)
* countTableRows (Swing)
* countTableColumns (Swing)
* getComponentPosition (Swing)
* setComponentsDirection (Swing)

## New classes
---------------------------
* Tables				-> Give tools to manipulate and work with jtables.
* ExcelFile				-> An entity for excel file.
* PdfFile				-> An entity for pdf file.
* JsonFile				-> An entity of json file.
* Exporter (interface)	-> Give export method.
* TableExportFactory	-> Generate an exporter to begin export.</br>

