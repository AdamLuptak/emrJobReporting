package com.here.owc;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.CreateStackRequest;
import com.amazonaws.services.cloudformation.model.DeleteStackRequest;
import com.amazonaws.services.cloudformation.model.DescribeStackResourcesRequest;
import com.amazonaws.services.cloudformation.model.DescribeStacksRequest;
import com.amazonaws.services.cloudformation.model.Stack;
import com.amazonaws.services.cloudformation.model.StackResource;
import com.amazonaws.services.cloudformation.model.StackStatus;


public class EmrJobReportingDeploy {

    public static final String STACK_NAME = "emrJobReporting";

    public static void main(String[] args) throws Exception {

        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        AmazonCloudFormation stackbuilder = AmazonCloudFormationClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_1)
                .build();

        System.out.println("===========================================");
        System.out.println("Getting Started with AWS CloudFormation");
        System.out.println("===========================================\n");

        String logicalResourceName = "SampleNotificationTopic";

        try {
//            // Create a stack
//            CreateStackRequest createRequest = new CreateStackRequest();
//            createRequest.setStackName(STACK_NAME);
//            createRequest.setTemplateBody(convertStreamToString(EmrJobReportingDeploy.class.getResourceAsStream("CloudFormationSample.template")));
//            System.out.println("Creating a stack called " + createRequest.getStackName() + ".");
//            stackbuilder.createStack(createRequest);
//
//            // Wait for stack to be created
//            // Note that you could use SNS notifications on the CreateStack call to track the progress of the stack creation
//            System.out.println("Stack creation completed, the stack " + STACK_NAME + " completed with " + waitForCompletion(stackbuilder, STACK_NAME));
//
//            // Show all the stacks for this account along with the resources for each stack
//            for (Stack stack : stackbuilder.describeStacks(new DescribeStacksRequest()).getStacks()) {
//                System.out.println("Stack : " + stack.getStackName() + " [" + stack.getStackStatus().toString() + "]");
//
//                DescribeStackResourcesRequest stackResourceRequest = new DescribeStackResourcesRequest();
//                stackResourceRequest.setStackName(stack.getStackName());
//                for (StackResource resource : stackbuilder.describeStackResources(stackResourceRequest).getStackResources()) {
//                    System.out.format("    %1$-40s %2$-25s %3$s\n", resource.getResourceType(), resource.getLogicalResourceId(), resource.getPhysicalResourceId());
//                }
//            }
//
//            // Lookup a resource by its logical name
//            DescribeStackResourcesRequest logicalNameResourceRequest = new DescribeStackResourcesRequest();
//            logicalNameResourceRequest.setStackName(STACK_NAME);
//            logicalNameResourceRequest.setLogicalResourceId(logicalResourceName);
//            System.out.format("Looking up resource name %1$s from stack %2$s\n", logicalNameResourceRequest.getLogicalResourceId(), logicalNameResourceRequest.getStackName());
//            for (StackResource resource : stackbuilder.describeStackResources(logicalNameResourceRequest).getStackResources()) {
//                System.out.format("    %1$-40s %2$-25s %3$s\n", resource.getResourceType(), resource.getLogicalResourceId(), resource.getPhysicalResourceId());
//            }

            // Delete the stack
            DeleteStackRequest deleteRequest = new DeleteStackRequest();
            deleteRequest.setStackName(STACK_NAME);
            System.out.println("Deleting the stack called " + deleteRequest.getStackName() + ".");
            stackbuilder.deleteStack(deleteRequest);

            // Wait for stack to be deleted
            // Note that you could used SNS notifications on the original CreateStack call to track the progress of the stack deletion
            System.out.println("Stack creation completed, the stack " + STACK_NAME + " completed with " + waitForCompletion(stackbuilder, STACK_NAME));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to AWS CloudFormation, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with AWS CloudFormation, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

    // Convert a stream into a single, newline separated string
    public static String convertStreamToString(InputStream in) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder stringbuilder = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringbuilder.append(line + "\n");
        }
        in.close();
        return stringbuilder.toString();
    }

    // Wait for a stack to complete transitioning
    // End stack states are:
    //    CREATE_COMPLETE
    //    CREATE_FAILED
    //    DELETE_FAILED
    //    ROLLBACK_FAILED
    // OR the stack no longer exists
    public static String waitForCompletion(AmazonCloudFormation stackbuilder, String stackName) throws Exception {

        DescribeStacksRequest wait = new DescribeStacksRequest();
        wait.setStackName(stackName);
        Boolean completed = false;
        String stackStatus = "Unknown";
        String stackReason = "";

        System.out.print("Waiting");

        while (!completed) {
            List<Stack> stacks = stackbuilder.describeStacks(wait).getStacks();
            if (stacks.isEmpty()) {
                completed = true;
                stackStatus = "NO_SUCH_STACK";
                stackReason = "Stack has been deleted";
            } else {
                for (Stack stack : stacks) {
                    if (stack.getStackStatus().equals(StackStatus.CREATE_COMPLETE.toString()) ||
                            stack.getStackStatus().equals(StackStatus.CREATE_FAILED.toString()) ||
                            stack.getStackStatus().equals(StackStatus.ROLLBACK_FAILED.toString()) ||
                            stack.getStackStatus().equals(StackStatus.DELETE_FAILED.toString())) {
                        completed = true;
                        stackStatus = stack.getStackStatus();
                        stackReason = stack.getStackStatusReason();
                    }
                }
            }

            // Show we are waiting
            System.out.print(".");

            // Not done yet so sleep for 10 seconds.
            if (!completed) Thread.sleep(10000);
        }

        // Show we are done
        System.out.print("done\n");

        return stackStatus + " (" + stackReason + ")";
    }

}