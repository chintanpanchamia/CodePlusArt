# [Code+Art](https://www.lib.ncsu.edu/codeart) Project : WolfClock

Authors: Chintan Panchamia, Aditya Shirode

### Description
WolfClock was created for Code+Art competition organized by NC State Libraries.
The idea originated on creating a good submission for the competition, over one weekend.
Displaying current date and time on a big screen, might not be innovative, but is essential and helpful in an almost invisible way. We tried to bring some WolfPack spririt into the visualization with pack/s of wolves wandering in the background.
The pack/s have different types of wolves, and they follow the Alpha, like they do in a real pack.
**Refer Note at bottom to get the submission executable**

### Motivation
The main inspiration was the need to create something simple and beautiful for the competition. Going by previous submissions and the kind of characteristics they exhibited, we had to make something that would scale well onto these large displays mounted across the Libraries on campus. We thought of common/popular data visualizations that are in use out there. These included scripts like Sentiment analyzers, Weather profilers, Word-map generators, mathematical patterns, etc.
While those are fun to work on, and depend on how well-suited the data is, we thought of working on something more essential and yet invisible in our day to day lives - Time.

It was decided that we would work on a clock visualization. After deciding this, we thought of fun ways to play with the animal mascot of the University. We knew were going to use Wolves for this. We used Photoshop to draw elaborate illustrations of the Wolf and tweaked the vector with color and shape for distinguishing between males and females of the various categories we considered for this program.
- Alpha
- Beta
- Pups
- Old/Aged
- Omega

Here's the original template sheet,

![wolfbase](https://cloud.githubusercontent.com/assets/13035693/24915642/b4ae5e50-1ea5-11e7-9ec8-cc101940924e.png)

However, after running into problems with optimization for rendering both SVGs and PNGs, we decided to make good use of the minimalistic aesthetic in our project. It was done by simplifying the wolves' designs, whilst retaining the color code. They looked like this, now that they had been rendered using a function that mapped vertices dynamically depending on size decided for the wolf-head. This made the rendering effortless and didn't tax the GPU on either of personal computer or macbook. Here's a close up of that shape. (while not being tiny or mobile)

![finalwolftemplate](https://cloud.githubusercontent.com/assets/13035693/24918218/a163f000-1ead-11e7-957e-aaae59faf156.png)


After thinking about taking this to the next level, we thought of mimicking an actual WolfPack, and using an AI program to do this. It would be a simple modification of the Flocking AI behavior - an algorithm put forth by Craig Reynolds. We used Daniel Shiffman's works in Processing as reference to implement this, with modifications of our own.

The NC State Campus, and the way its inhabitants move about was part of the inspiration for this project. After noticing that the wolves following a path behind the Alpha in their pack didn't look as visually appealing as we had assumed, we decided to add something from this inspiration. By tweaking the flocking characteristics, we got a category of wolves to follow the one level superior to itself while the Alpha/s wander as they will. The pack breaks down and follows anybody they deem influential. Sometimes they follow their own kind. Sometimes they aspire for something greater and follow their superiors. Sometimes they move around following different wolves. Sometimes they are pushed by their flock into following other wolves they are not completely sure about, or can't even sense within their perception-range.
While this is now peculiarly different than how wolves operate in nature, it is strangely characteristic of how we humans function, and since we, at NC State are our own Wolves, we couldn't help but see the similarity.

### Creators
**Chintan Panchamia** _cpancha_@ncsu.edu
I'm a graduate student at NC State University, about to graduate in May 2017, with a Master's Degree in Computer Science. My hobbies include designing, coding, writing, some reading, sketching, and running.
You can find me here, on [Linkedin][1], [Twitter][2] and [Facebook][3]

[1]: http://wwww.linkedin.com/in/chintanpanchamia
[2]: http://www.twitter.com/chinpanchamia
[3]: http://www.facebook.com/chintan.panchamia

**Aditya Shirode** _avshirod_@ncsu.edu
I'm a graduate student at NC State University, graduating at the same time, with a Master's Degree in Computer Science. My hobbies include reading, football, coding, and playing racquetball.
You can find me here, on [Linkedin][4], [Twitter][5] and [Facebook][6]

[4]: http://www.linkedin.com/in/adityavshirode
[5]: http://www.twitter.com/Anthaceorote
[6]: https://www.facebook.com/anthaceorote


**NOTE: Submission:Executable.zip contains the .exe file as requested for the submission. Download the Zip and directly run the executable.**
