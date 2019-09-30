# android-bloc
A BLoC implementation using Kotlin Coroutines

# Architecture

![Architecture][Architecture]

[Original Image](https://felangel.github.io/bloc/#/architecture?id=architecture)

# Why Bloc?

> Bloc makes it easy to separate presentation from business logic, making your code _fast_, _easy to test_, and _reusable_.

When building production quality applications, managing state becomes critical.

As developers we want to:

- know what state our application is in at any point in time.
- easily test every case to make sure our app is responding appropriately.
- record every single user interaction in our application so that we can make data-driven decisions.
- work as efficiently as possible and reuse components both within our application and across other applications.
- have many developers seamlessly working within a single code base following the same patterns and conventions.
- develop fast and reactive apps.

Bloc was designed to meet all of those needs and many more.

There are many state management solutions and deciding which one to use can be a daunting task.

Bloc was designed with three core values in mind:

- Simple
  - Easy to understand & can be used by developers with varying skill levels.
- Powerful
  - Help make amazing, complex applications by composing them of smaller components.
- Testable
  - Easily test every aspect of an application so that we can iterate with confidence.

Bloc attempts to make state changes predictable by regulating when a state change can occur and enforcing a single way to change state throughout an entire application.

[Why Bloc original content](https://felangel.github.io/bloc/#/whybloc)

# Core Concepts

There are several core concepts that are critical to understanding how to use Bloc.

In the upcoming sections, we're going to discuss each of them in detail as well as work through how they would apply to a real-world application: a counter app.

[Core Concepts original content](https://felangel.github.io/bloc/#/coreconcepts?id=core-concepts)

## Events

> Events are the input to a Bloc. They are commonly dispatched in response to user interactions such as button presses or lifecycle events like page loads.

When designing an app we need to step back and define how users will interact with it. In the context of our counter app we will have two buttons to increment and decrement our counter.

When a user taps on one of these buttons, something needs to happen to notify the "brains" of our app so that it can respond to the user's input; this is where events come into play.

We need to be able to notify our application's "brains" of both an increment and a decrement so we need to define these events.

```kotlin
sealed class CounterEvent {
    object Decrement : CounterEvent()
    object Increment : CounterEvent()
}
```

In this case, we can represent the events using an `sealed class`.

At this point we have defined our first event! Notice that we have not used Bloc in any way so far and there is no magic happening; it's just plain Kotlin code.

[Events original content](https://felangel.github.io/bloc/#/coreconcepts?id=events)

## States

> States are the output of a Bloc and represent a part of your application's state. UI components can be notified of states and redraw portions of themselves based on the current state.

So far, we've defined the two events that our app will be responding to: `CounterEvent.Increment` and `CounterEvent.Decrement`.

Now we need to define how to represent the state of our application.

Since we're building a counter, our state is very simple: it's just an integer which represents the counter's current value.

We will see more complex examples of state later on but in this case a primitive type is perfectly suitable as the state representation.

[States original content](https://felangel.github.io/bloc/#/coreconcepts?id=states)

## Transitions

> The change from one state to another is called a Transition. A Transition consists of the current state, the event, and the next state.

As a user interacts with our counter app they will trigger `Increment` and `Decrement` events which will update the counter's state. All of these state changes can be described as a series of `Transitions`.

For example, if a user opened our app and tapped the increment button once we would see the following `Transition`.

```kotlin
Transition(currentState=0, event=CounterEvent$Increment@6e93bdec, nextState=1)
```

Because every state change is recorded, we are able to very easily instrument our applications and track all user interactions & state changes in one place. In addition, this makes things like time-travel debugging possible.

[Transitions original content](https://felangel.github.io/bloc/#/coreconcepts?id=transitions)

## Blocs

> A Bloc (Business Logic Component) is a component which converts incoming `Events` into a `Flow` of outgoing `States`. Think of a Bloc as being the "brains" described above.

> Every Bloc must extend the abstract class `Bloc` class and inform a coroutine scope that will be used to manager events.

```kotlin
import 'package:bloc/bloc.dart';

class CounterBloc(eventScope: CoroutineScope) : Bloc<CounterEvent, Int>(eventScope) {

}
```

In the above code snippet, we are declaring our `CounterBloc` as a Bloc which converts `CounterEvents` into `ints`.

> Why I have to inform a coroutine scope always? Because is more flexible and make ease to test.

> Every Bloc must define an initial state which is the state before any events have been recieved.

In this case, we want our counter to start at `0`.

```kotlin
override val initialState: Int = 0
```

> Every Bloc must implement a function called `mapEventToState`. The function takes the incoming `event` as an argument and emit new `states` which is consumed by the presentation layer. We can access the current bloc state at any time using the `currentState` property.

```kotlin
override suspend fun FlowCollector<Int>.mapEventToState(event: CounterEvent) {
    val nextState = when (event) {
        is CounterEvent.Decrement -> currentState - 1
        is CounterEvent.Increment -> currentState + 1
    }
    emit(nextState)
}
```

At this point, we have a fully functioning `CounterBloc`.

```kotlin
import br.com.programadorthi.bloc.Bloc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.FlowCollector

class CounterBloc(eventScope: CoroutineScope) : Bloc<CounterEvent, Int>(eventScope) {

    override val initialState: Int = 0

    override suspend fun FlowCollector<Int>.mapEventToState(event: CounterEvent) {
        val nextState = when (event) {
            is CounterEvent.Decrement -> currentState - 1
            is CounterEvent.Increment -> currentState + 1
        }
        emit(nextState)
    }

}
```

> Blocs will ignore duplicate states. If a Bloc emit `State state` where `currentState == state`, then no transition will occur and no change will be made to the `Flow<State>`.

At this point, you're probably wondering _"How do I notify a Bloc of an event?"_.

> Every Bloc has a `dispatch` method. `Dispatch` takes an `event` and triggers `mapEventToState`. `Dispatch` may be called from the presentation layer or from within the Bloc and notifies the Bloc of a new `event`.

We can create a simple application which counts from 0 to 3.

```kotlin
fun main() = runBlocking {
    val scope = CoroutineScope(Dispatchers.Default)
    val bloc = CounterBloc(scope)

    launch {
        for (value in 0..2) {
            delay(1000)
            bloc.dispatch(CounterEvent.Increment)
        }
    }
    
    scope.launch {
        bloc.state.collect { value ->
            println(">>>>>> value: $value")
        }
    }
}
```

The `Transitions` in the above code snippet would be

```kotlin
Transition(currentState=0, event=CounterEvent$Increment@6e93bdec, nextState=1)
Transition(currentState=1, event=CounterEvent$Increment@6e93bdec, nextState=2)
Transition(currentState=2, event=CounterEvent$Increment@6e93bdec, nextState=3)
```

Unfortunately, in the current state we won't be able to see any of these transitions unless we override `onTransition`.

> `onTransition` is a method that can be overridden to handle every local Bloc `Transition`. `onTransition` is called just before a Bloc's `state` has been updated.

> **Tip**: `onTransition` is a great place to add bloc-specific logging/analytics.

```kotlin
override fun onTransition(transition: Transition<CounterEvent, Int>) {
    super.onTransition(transition)
    println(transition)
}
```

Now that we've overridden `onTransition` we can do whatever we'd like whenever a `Transition` occurs.

Just like we can handle `Transitions` at the bloc level, we can also handle `Exceptions`.

> `onError` is a method that can be overridden to handle every local Bloc `Exception`. By default all exceptions will be ignored and `Bloc` functionality will be unaffected.

> **Tip**: `onError` is a great place to add bloc-specific error handling.

```kotlin
override fun onError(cause: Throwable) {
    super.onError(cause)
    println(cause)
    // send cause to crashlytics
}
```

Now that we've overridden `onError` we can do whatever we'd like whenever an `Exception` is thrown.

You can check when an `Event` was dispatched if you override `onEvent`.

> `onEvent` is a method that can be overridden to handle every local Bloc `Event`. `onEvent` is called just before the dispatched event to be processed;

> **Tip**: `onEvent` is a great place to add bloc-specific logging/analytics.

```kotlin
override fun onEvent(event: CounterEvent) {
    super.onEvent(event)
    println(event)
    // send statistics to analytics
}
```

Now that we've overridden `onEvent` we can do whatever we'd like whenever a `Event` occurs.

If you would like to avoid an `Event` to be processed you can override `computeEvent`.

> `computeEvent` is a method that can be overridden to avoid an `Event` to be processed. `computeEvent` is called after `onEvent` and just before the dispatched event to be processed. Default behavior always returns `true`.

> **Tip**: `computeEvent` is a great place to add a custom logic to process an `Event`.

```kotlin
override suspend fun computeEvent(event: CounterEvent): Boolean {
    // Only Decrement events will be processed
    return when (event) {
        is CounterEvent.Decrement -> true
        // Avoiding Increment events to be processed
        is CounterEvent.Increment -> false
    }
}
```

Now that we've overridden `computeEvent` we can do whatever we'd like whenever a `Event` occurs.

> **There is a `computeState` version that can be used to avoid `State` to be emitted**

If you would like to transform an `Event` in another `Event` you can override `transformEvent`.

> `transformEvent` is a method that can be overridden to transform an `Event` in another `Event`. `transformEvent` is called after `computeEvent`. Default behavior always returns the dispatched event.

> **Tip**: `transformEvent` is a great place to add a custom logic to redirect an `Event` to another `Event`.

```kotlin
override suspend fun transformEvent(event: CounterEvent): CounterEvent {
    // Making the user crazy.
    // When he clicks decrement, we increment :p
    // When he clicks increment, we decrement. :p
    return when (event) {
        is CounterEvent.Decrement -> CounterEvent.Increment
        is CounterEvent.Increment -> CounterEvent.Decrement
    }
}
```

Now that we've overridden `transformEvent` we can do whatever we'd like whenever a `Event` occurs.

> **There is a `transformState` version that can be used to convert a `State` in another `State`**

[Blocs original content](https://felangel.github.io/bloc/#/coreconcepts?id=blocs)

## BlocInterceptor

One added bonus of using Bloc is that we can have access to all `Transitions` in one place. Even though in this application we only have one Bloc, it's fairly common in larger applications to have many Blocs managing different parts of the application's state.

If we want to be able to do something in response to all `Transitions` we can simply create our own `BlocInterceptor`.

```kotlin
class MainBloc : BlocInterceptor {
    override fun <Event, State> onTransition(transition: Transition<Event, State>) {
        Logger.d(">>>>> Global onTransition: $transition")
    }
}
```

> **Note**: All we need to do is extend `BlocInterceptor` and override the `onTransition` method.

In order to tell Bloc to use our `MainBloc`, we just need to tweak our `main` function.

```kotlin
fun main() = runBlocking {
    BlocInterceptor.initBlocInterceptor(MainBloc())
    
    val scope = CoroutineScope(Dispatchers.Default)
    val bloc = CounterBloc(scope)

    launch {
        for (value in 0..2) {
            delay(1000)
            bloc.dispatch(CounterEvent.Increment)
        }
    }
    
    scope.launch {
        bloc.state.collect { value ->
            println(">>>>>> value: $value")
        }
    }
}
```

If we want to be able to do something in response to all `Events` dispatched, we can also override the `onEvent` method in our `MainBloc`.

```kotlin
class MainBloc : BlocInterceptor {
    override fun <Event> onEvent(event: Event) {
        Logger.i(">>>>> MainBloc onEvent: $event")
    }

    override fun <Event, State> onTransition(transition: Transition<Event, State>) {
        Logger.d(">>>>> MainBloc onTransition: $transition")
    }
}
```

If we want to be able to do something in response to all `Exceptions` thrown in a Bloc, we can also override the `onError` method in our `MainBloc`.

```kotlin
class MainBloc : BlocInterceptor {
    override fun onError(cause: Throwable) {
        Logger.e(cause, ">>>>> MainBloc onError")
    }

    override fun <Event> onEvent(event: Event) {
        Logger.i(">>>>> MainBloc onEvent: $event")
    }

    override fun <Event, State> onTransition(transition: Transition<Event, State>) {
        Logger.d(">>>>> MainBloc onTransition: $transition")
    }
}
```

> **Note**: `BlocInterceptor` is a singleton which oversees all Blocs and delegates responsibilities to the `BlocInterceptor`.

[BlocDelegate original content](https://felangel.github.io/bloc/#/coreconcepts?id=blocdelegate)

## Credits

- [Bloc] - a predictable state management library for Dart that was used as base to this project.
- [Norris] - for the project structure and inspiration using Kotlin Coroutines.
- [Jetbrains] - for the amazing developer experience around Kotlin and Coroutines

## Author

Thiago Santos (follow me on [Twitter])


[Bloc]: <https://felangel.github.io/bloc/#/>
[Norris]: <https://github.com/dotanuki-labs/norris>
[Jetbrains]: <https://www.jetbrains.com/>
[Twitter]: <https://twitter.com/programadorthi>
[Architecture]: https://felangel.github.io/bloc/assets/bloc_architecture.png
