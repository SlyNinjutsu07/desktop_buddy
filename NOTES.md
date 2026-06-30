### Notes for `desktop_buddy`

The **goal** of this project is to try to get 
a movable sprite on your desktop, of which is
interactable and responsive.

#### `Current Tasks`
- [ ] `Implementing random movement for the sprite`
- [ ] `Adding buttons and functionality to the sprite`
- [ ] `Second Brain features (markdown editing)`

#### 5.31.26
Began working on the implementation of randomNewPos.
Later decided to work it into its own class called
`SpritePos`. 

`TODO`: Implement `Timer`s for the movement
of the `JFrame` for the sprite/window.

#### 6.2.26

Started working on the movement implementation. Learned a new fact:
`Iterating a local variable of a method within the Timer's lambda call
is illegal because a method puts its local variables on the stack. Once
its respective stackframe is returned, the local variable disappears, but
the Timer's lambda still calls it. So I had to make _elapsed_time_ and
_goal_time_ into properties/fields of the Sprite class`.

Implemented the goal timers for both IDLE and MOVING states. I don't think it works yet though.

#### 6.7.26

I think I'm going to go ahead and give up on the movement. When I think
about it, the movement isn't even a priority. I should probably work
more on the functionality of the project and focus on the movement
and personality of the sprite much later. After all, I'm developing 
this is as something I can **USE**.

#### 6.13.26

Added a popup menu when left-clicking the sprite
Also added a `NoteManager` class, which will be a window
for managing all the markdown files for note-taking.
*I need to learn how to customize it and get it to work though*

Plan: **Add a button for adding markdown files/notes
and customize the NoteManager**

#### 6.29.26

Refactored the project to use packages. Files are now organized under `com.desktopbuddy`
with `ui` and `data` subpackages. `Settings.java` was split into `SettingsData.java` (data)
and `SettingsWindow.java` (UI). Added `maven-shade-plugin` to `pom.xml` so that
`mvn package` {cli command btw} produces a single fat jar with all dependencies bundled, ready for distribution.
