#SLogo README.md

##Project team members:
* Robert Steilberg | rhs16
* Delia Li | dl202
* Ezria Lieblich | eml36
* Grayson Wise | gkw

##Time commitment
* Started 4 October 2016
* Finished 1 November 2016
* 60+ hours spent on the project

##Roles
* Robert: core back end engine, building parse tree, executing parse tree, interfacing with GUI, commands, error checking, refactoring the back end, saving files from the front end GUI
* Ezra: core back end engine, building parse tree, executing parse tree, interfacing with GUI, commands, reflecting turtle changes in the front end, all turtle and display commands, interfacing with the front end GUI, refactoring
* Delia: front end GUI, interfacing with the back end
* Grayson: front end GUI, interfacing with the back end

##Resources
* Online JavaDocs from Oracle
* Prof. Duvall for organizing the back end commands
* stackoverflow.com

##Initial files
* The regular expression example from the advanced Java examples shown in class was used for recognizing commands in the back end.

##Test files
* All of the SLogo files specified on the course website are included and were used to test the functionality of the SLogo commands in the project.

##Data or resource files
* LOGO example files, properties files were used to define the regular expression matches for LOGO commands and LOGO commands' translations into different languages; properties files were also used for other settings and for keeping track of class locations within the file (for reflection)
* JPG, PNG, and GIF images used in the GUI

##Relevant info about the project
* No command-line arguments are needed. The project simply needs to be run, and LOGO commands can be inputted in the Editor pane and then executed through pressing the Run button. The Flower example file is a particularly interesting file to run.
* Note that ask and askWith revert the list of active turtles to the previous list of active Turtles. That means that
if a tell is nested in an ask it will still get reverted to the tell before the ask. This was a design choice that was
made after Prof Duvall responed to a Piazza post

##Known bugs
* Variable tableview does not clear properly
This happens when the "clear" button is pressed but more variables are defined. Previously cleared variables reappear in the chart. Since this does not impact the functionality (variables needed would have updated to the correct values), we decided to leave it.
* Turtle path sometimes does not point perfectly at turtle if it goes off the screen. Only applies to straight paths that are long in distance.
We think this is because the edge checking does not account for linear intersections of the path with the edge of the display. Since this is very minor, we left it as is.
* Display mappings are not updated properly when the front end changes the penColor or Background color.
This is because the front end allows to change to any color whereas the backend only maps to a limited amount of colors.
We decided to just leave the index when they call get pen color if they change it in the front end

##Extra features
* Extra front end features include multiple workspaces, display commands that interact with the display of the turtle (i.e. color, shape, pen properties, etc.), saving and loading workspace preferences, choosing a turtle to command by clicking on it, and getting information about a turtle by hovering over it.
* Extra back end features include commanding more than one turtle sprite, additional display and multiple turtle commands, saving and opening LOGO files, grouping commands, variable scoping, and recursion.

##Impressions
* Robert: I thoroughly enjoyed working on this project; this is by far the largest and most complex project that I have ever worked on. I think it was a great exercise for learning advanced teamwork skills and bringing together multiple components of a project after being implemented completely separately. I think the extensions were also a fun challenge to implement and really tested how well we could extend our design without abandoning it.

* Grayson: I found this to be a very challenging, yet rewarding, project. This project was larger than anything else I have ever worked on and really tested my coding, design, and communication skills. I also discovered, when comparing this experience with that of the last project, that I enjoy front-end coding much more than back-end, and will hopefully get to work on the front-end for VOOGASalad. 
  
* Delia: I learned so much while working on this project, from the team discussions to researching the best way to implement front end features to gaining valuable skills in pair programming and design considerations. It really tested our planning skills from the initial meeting since every subsequent feature depended on how well we designed our project. This project was also a lot of fun because it challenged me to not only create something both easy to use and functionally powerful, but also to make it graphically and artistically pleasing to the user.
