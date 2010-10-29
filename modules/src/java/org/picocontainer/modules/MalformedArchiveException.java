/**
 * 
 */
package org.picocontainer.modules;

/**
 * Exception that indicates that the deployment folder/archive didn't have the 
 * expected folders or files.
 * @author Michael Rimov, Centerline Computers, Inc.
 *
 */
@SuppressWarnings("serial")
public class MalformedArchiveException extends DeploymentException {

	/**
	 * @param message
	 */
	public MalformedArchiveException(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param t
	 */
	public MalformedArchiveException(String message, Throwable t) {
		super(message, t);
	}

	/**
	 * @param t
	 */
	public MalformedArchiveException(Throwable t) {
		super(t);
	}

}
