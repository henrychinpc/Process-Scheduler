# Process-Scheduler

## Algorithm and Analysis Assignment 1

#### Objectives

There are three key objectives for this assignment:

- Understand how a real problem can be mapped to various representations and their operations.
- Use a number of fundamental data structures to implement the runqueue abstract data type.
- Evaluate and contrast the performance of the data structures with respect to dif- ferent usage scenarios and input data.

#### Overview

Scheduling is the process of arranging, controlling and optimizing tasks and workloads. In computing, scheduling concerns about which task is assigned to resources that complete the task. The task may be virtual computation elements such as threads, processes or data flows, which are in turn scheduled onto hardware resources such as processors, network links or expansion cards [2]. Scheduling is an intrinsic part of the execution model of a computer system. It makes it possible to have computer multitasking with a single central processing unit (CPU). For more detailed information about scheduling, please refer to [2].

A process scheduler carries out the scheduling activity in an operating system. It arranges active processes in a runqueue. The runqueue represents a timeline for the scheduled processes. For example, the Completely Fair Scheduler (CFS) is a process scheduler for the 2.6.23 (October 2007) release of the Linux kernel. Schedulers like the CFS keep track of time (in nanoseconds) a process has executed on processor, called vruntime. In this assignment, we will implement and analysis CFS types of schedulers that keep track of vruntime of a process. Processes that has got a small amount of time are boosted to the front of the runqueue to get the processor, those who got bigger amount of time are thwarted [1]. When the scheduler is invoked to run a new process: the process with the lowest vruntime from the runqueue is chosen, and sent for execution, then

1. If the process simply completes execution, it is removed from the runqueue

2. Otherwise, if it is interrupted for a reason, it is reinserted into the runqueue based on its new vruntime. If there are multiple processes has the same vruntime, they follow a First-In-First-Out (FIFO) ordered.

This type of schedulers commonly require efficient data structure implementation of the runqueue to schedule new process, delete process, track process information and high- performance code to generate the schedules. Despite the name, the runqueue need not be implemented in the traditional way, e.g., as an array. In this assignment, we defines the runqueue as an abstract data type similar to a PriorityQueue and consider the following two representations:

- The Sequential Representation
- The Tree Representation

We will implement both representations for the runqueue and evaluate how well they perform when representing some given runqueues and compute the average speed of various operations.
