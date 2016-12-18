###Estimation
I believe that it will take me 15 minutes to add these new commands. I believe I need to add two commands that extend the
DisplayProperties command since these additional commands are similar to other display commands in that it is
associated with the GUI Display. In addition, I need to add the commands path to the resource file which handles the 
reflection to instantiate the commands. Finally I need to create the skeleton methods in the GUI that the commands will 
attach their listeners to and call when the values change so I dont get an error. 

###Review
It took me about 15 minutes to implement these two commands. I needed to update 5 files. I needed to create 2 command
classes for stamp and clearstamps which extended DisplayCommand which has access to the DisplayProperties class. 
DisplayProperties has listeners to the front end which calls methods to the front end. In DisplayProperties class I 
needed to add ObservableProperties for stamp and clear stamp commands and attach change listeners to those properties.
Those would call methods in the front end that would update the GUI when the command is called. Therefore, I needed
to add methods that the DisplayProperties class would call. Finally I needed to add a the class locations in the 
ClassLocations resource file and add it so the reflection would create the stamp and clearstamp commands.

I did not get it right on the first try as I forgot to add the two commands to the resource file so the reflection failed
but that was a quick and easy fix.

###Analysis
I think the documentation worked pretty well for this assignment. The command classes were divided into corresponding
subpackages so it was very clear where the new commands should go in package and also that the classes needed to extend
DisplayCommand since all my other classes in that package were documented and explained why it needed to extend it.
The code was well documented and as good as I could remember as its very to identify which commands go where and also
what the purpose of each class that extends does. I think outside of the command package names, the naming of the 
packages could be improved. The Commands named make sense but package names such as backendInterface, and backendinterpreter
probably don't make as much sense for people who didn't work on the program.
I think if I had never written this code it would be pretty easy to understand what to do with the program. All the commands
and superclasses that are extended are well commented so it is very easy to identify which superclasses to extend and
where to place the commands in the subpackages, as they are well named.