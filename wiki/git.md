# Git

## Introduction

Git is a relatively simple repository system under the hood, complete with an unstructured use of plant like instructions to use it. On this page we will aim to make this much clearer.

## Pulling

To pull from Git, first we want to make sure we know what changes have happened in the remote side.

    git fetch

Now, we want to see where our local repository is in respect to the origin (server) repository.

    git status

If we're up to date, there's no reason to do anything else. If we are behind by any commits, we will need to get those.

Before we can do that, we need to get any uncommitted files out of the way so that when we pull they don't clash. You don't always have to do this, it's only if you have uncommitted changes.

    git stash

Now we are in a position to pull the changes from the remote.

    git pull

Now we have pulled the latest changes, if we stash our changes we can replay these back on top of master.

    git stash pop

You should now have the latest changes for your current branch!

## Committing

### Visually

This is the way I would always recommend as you get used to Git and even in general. Git has it's own GUI you can use for this purpose.

Install the GUI with:

    sudo apt-get update
    sudo apt-get upgrade
    sudo apt-get install git-gui

Once installed, the way you use it is by entering:

    git gui

In this mode we can commit entire files, blocks of lines, individual lines, revert changes and much more. For for more information on this, please use your favourite search engine to find out more.

### Command Line

The command like version of committing can be much more difficult. Firstly we want to stage our files for commit:

    git add file1 file2 ... fileN

Now we want to create a commit for the changes we have staged:

    git commit -m "Message for Git here"

Typically, the message will be less than 80 characters, start with a capital letter and not end with punctuation. This is just an unspoken standard that appeared to have arose out of the chaos.

## Pushing

It's very important that you have pulled all changes from remote very recently. If you haven't you can cause issues with the remote, like the following:

     |
     |
    ( )
     | \
     |   \
    ( )    \
     |      |
     |      |
     |     ( )
     |      |
     |      |
    ( )     |
     |      |
     |      |
      \     |
        \   |
          \ |
           ( )
            |
            |

Disgusting right? In reality it means a merge happened in origin. Not so bad right? Wrong! If a merge fails in origin, you can break the system for everybody. It can also become much more difficult to understand the more and more this happens.

**NOTE:** You can normally get a visual picture of what is happening by running `gitk --all`. If you haven't got this program, you can install it via `sudo apt-get install gitk`.

To prevent this, we make sure we have followed all of the instructions in the `Pulling` section. This means all of the merge issues are dealt with locally. Only then are we able to push using the following command:

    git push
