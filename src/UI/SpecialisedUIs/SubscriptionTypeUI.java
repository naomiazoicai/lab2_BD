package UI.SpecialisedUIs;

import UI.UI;
import controller.SubscriptionTypeController;
import controller.interfaces.ISubscriptionTypeController;
import domain.money.SubscriptionType;
import repository.exceptions.ObjectAlreadyContained;
import repository.exceptions.ObjectNotContained;

import java.util.ArrayList;
import java.util.Objects;

public class SubscriptionTypeUI extends UI<SubscriptionType> {
    private static SubscriptionTypeUI instance;

    private final ISubscriptionTypeController subscriptionTypeController;

    private SubscriptionTypeUI(SubscriptionTypeController subscriptionTypeController)
    {
        super(subscriptionTypeController);
        this.subscriptionTypeController = subscriptionTypeController;
    }

    public static SubscriptionTypeUI getInstance()
    {
        if (instance == null) instance = new SubscriptionTypeUI(SubscriptionTypeController.getInstance());
        return instance;
    }

    @Override
    public void run()
    {
        terminal.printMessage("Subscription type UI is running...");
        String choice = terminal.subscriptionTypeUiMenu();
        // If choice == X -> return to main menu
        while (!Objects.equals(choice, "x") && !Objects.equals(choice, "X"))
        {
            switch (choice)
            {
                case "1": addEntity(); break;
                case "2": updateEntity(); break;
                case "3": deleteEntity(); break;
                case "4": searchByPartialName(); break;
                case "5": addRoomToSubscription(); break;
                case "6": removeRoomFromSubscription(); break;
                case "7": showAll(); break;
            }
            terminal.pressEnterToContinue();
            choice = terminal.subscriptionTypeUiMenu();
        }
    }

    @Override
    public void addEntity() {
        String name = terminal.readSubscriptionTypeName();
        // Check if name exists
        while (subscriptionTypeController.usernameInRepo(name))
        {
            terminal.printMessage("Subscription Type name already in repo! Choose another");
            name = terminal.readSubscriptionTypeName();
        }
        // Read other attributes
        String description = terminal.readDescription();
        double price = terminal.readPrice();
        // Create and add new subscription type
        SubscriptionType subscriptionType = new SubscriptionType(name, description, price);
        try {
            controller.add(subscriptionType);
        } catch (ObjectAlreadyContained e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEntity() {
        String name = terminal.readSubscriptionTypeName();
        if (subscriptionTypeController.usernameInRepo(name))
        {
            SubscriptionType subscriptionType = subscriptionTypeController.searchByUsername(name);
            try {
                controller.delete(subscriptionType);
                terminal.printMessage("Subscription type deleted: " + subscriptionType);
            } catch (ObjectNotContained e)
            {
                throw new RuntimeException();
            }
        } else terminal.printMessage("Subscription type name was not found");
    }

    @Override
    public void updateEntity() {
        String name = terminal.readSubscriptionTypeName();
        // Check if name exists
        if (subscriptionTypeController.usernameInRepo(name)) {
            SubscriptionType existingSubscriptionType = subscriptionTypeController.searchByUsername(name);

            // Display existing subscription type details
            terminal.printMessage("Existing Subscription Type Details:\n" + existingSubscriptionType);

            // Prompt user for updated details
            String updatedDescription = terminal.readDescription();
            double updatedPrice = terminal.readPrice();

            // Update the subscription type details
            existingSubscriptionType.setDescription(updatedDescription);
            existingSubscriptionType.setPrice(updatedPrice);

            // Display updated subscription type details
            terminal.printMessage("Updated Subscription Type Details:\n" + existingSubscriptionType);

            // Save the updated subscription type to the repository
            try {
                controller.update(existingSubscriptionType);
                terminal.printMessage("Subscription type updated successfully!");
            } catch (ObjectNotContained e) {
                terminal.printMessage("Error updating subscription type: " + e.getMessage());
            }
        } else {
            terminal.printMessage("Subscription type name was not found");
        }
    }

    public void searchByPartialName()
    {
        String name = terminal.readSubscriptionTypeName();
        ArrayList<SubscriptionType> subscriptionTypes = subscriptionTypeController.searchByPartialUsername(name);
        terminal.printArrayList(subscriptionTypes);
    }

    public void addRoomToSubscription()
    {
        // Room
        terminal.printMessage("Enter room id: ");
        int roomId = terminal.readId();
        if (!subscriptionTypeController.roomIdInRepo(roomId))
        {
            terminal.printMessage("Room id was not found");
            return;
        }
        // Trainer
        terminal.printMessage("Enter subscription type name: ");
        String subscriptionTypeName = terminal.readSubscriptionTypeName();
        if (!subscriptionTypeController.usernameInRepo(subscriptionTypeName))
        {
            terminal.printMessage("Subscription type was not found");
            return;
        }
        // Add
        try {
            subscriptionTypeController.addRoomToSubscription(subscriptionTypeName, roomId);
        } catch (ObjectNotContained e) {
            throw new RuntimeException(e);
        }
        terminal.printMessage("Successful!");
    }

    public void removeRoomFromSubscription()
    {
        // Room
        terminal.printMessage("Enter room id: ");
        int roomId = terminal.readId();
        if (!subscriptionTypeController.roomIdInRepo(roomId))
        {
            terminal.printMessage("Room id was not found");
            return;
        }
        // Trainer
        terminal.printMessage("Enter subscription type name: ");
        String subscriptionTypeName = terminal.readSubscriptionTypeName();
        if (!subscriptionTypeController.usernameInRepo(subscriptionTypeName))
        {
            terminal.printMessage("Subscription type was not found");
            return;
        }
        // Add
        try {
            subscriptionTypeController.removeRoomFromSubscription(subscriptionTypeName, roomId);
        } catch (ObjectNotContained e) {
            throw new RuntimeException(e);
        }
        terminal.printMessage("Successful!");
    }
}
